package dev.vality.cm.handler;

import dev.vality.cm.AbstractIntegrationTest;
import dev.vality.cm.meta.UserIdentityEmailExtensionKit;
import dev.vality.cm.meta.UserIdentityIdExtensionKit;
import dev.vality.cm.meta.UserIdentityRealmExtensionKit;
import dev.vality.cm.meta.UserIdentityUsernameExtensionKit;
import dev.vality.cm.service.ConversionWrapperService;
import dev.vality.cm.util.MockUtil;
import dev.vality.damsel.claim_management.*;
import dev.vality.damsel.domain.CategoryRef;
import dev.vality.damsel.domain.ShopDetails;
import dev.vality.damsel.domain.ShopLocation;
import dev.vality.damsel.msgpack.Value;
import dev.vality.woody.thrift.impl.http.THSpawnClientBuilder;
import dev.vality.cm.util.ServiceUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.annotation.Repeat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ClaimManagementHandlerTest extends AbstractIntegrationTest {

    @Autowired
    private ConversionWrapperService conversionWrapperService;

    private ClaimManagementSrv.Iface client;

    @MockBean
    private KafkaTemplate<String, TBase> kafkaTemplate;

    @Before
    public void setUp() throws URISyntaxException {
        client = new THSpawnClientBuilder()
                .withAddress(new URI("http://localhost:" + port + "/v1/cm"))
                .withNetworkTimeout(-1)
                .withMetaExtensions(
                        List.of(
                                UserIdentityIdExtensionKit.INSTANCE,
                                UserIdentityUsernameExtensionKit.INSTANCE,
                                UserIdentityEmailExtensionKit.INSTANCE,
                                UserIdentityRealmExtensionKit.INSTANCE
                        )
                )
                .build(ClaimManagementSrv.Iface.class);

        Mockito.when(kafkaTemplate.send(any(), any(), any())).thenReturn(new AsyncResult<>(null));
    }

    @Test
    public void testCreateClaimAndGet() {
        Claim claim = ServiceUtils.createClaim(client, conversionWrapperService, "party_id", 5);
        assertEquals(claim, ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId())));
    }

    @Test
    public void testCreateClaimAndUpdate() {
        Claim claim = ServiceUtils.createClaim(client, "party_id",
                ServiceUtils.generateModifications(conversionWrapperService,
                        () -> MockUtil.generateTBaseList(Modification.party_modification(
                                new PartyModification()), 5)));
        assertEquals(claim, ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId())));
        ServiceUtils.runService(() -> client.updateClaim("party_id", claim.getId(), 0,
                ServiceUtils.generateModifications(conversionWrapperService, () -> MockUtil
                        .generateTBaseList(Modification.claim_modification(new ClaimModification()), 5))));
    }

    @Test(expected = InvalidChangeset.class)
    public void testTryingToInvalidChansetWhenCreateClaimAndGet() {
        Modification modification = MockUtil.generateTBase(Modification.class);
        ServiceUtils.createClaim(client, "party_id", List.of(modification, modification));
    }

    @Repeat(5)
    @Test(expected = ChangesetConflict.class)
    public void testTryingToGetConflictWhenUpdate() {
        List<Modification> modifications = ServiceUtils.generateModifications(conversionWrapperService, 1);
        Claim claim = ServiceUtils.createClaim(client, "party_id", modifications);
        assertEquals(claim, ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId())));
        ServiceUtils.runService(() -> client.updateClaim("party_id", claim.getId(), 0, modifications));
    }

    @Test(expected = ClaimNotFound.class)
    public void testClaimNotFound() {
        ServiceUtils.callService(() -> client.getClaim("party_id", -1));
    }

    @Test(expected = ClaimNotFound.class)
    public void testGetClaimWithWrongParty() {
        Claim claim = ServiceUtils.createClaim(client, conversionWrapperService, "party_id", 5);
        assertEquals(claim, ServiceUtils.callService(() -> client.getClaim("wrong_party", claim.getId())));
    }

    @Test
    public void testCreateClaimAndAccept() {
        Claim claim = ServiceUtils.createClaim(client, conversionWrapperService, "party_id", 5);
        ServiceUtils.runService(() -> client.acceptClaim("party_id", claim.getId(), 0));
        assertEquals(ClaimStatus.pending_acceptance(new ClaimPendingAcceptance()),
                ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
    }

    @Test
    public void testCreateClaimAndDeny() {
        Claim claim = ServiceUtils.createClaim(client, conversionWrapperService, "party_id", 5);
        ServiceUtils.runService(() -> client.denyClaim("party_id", claim.getId(), 0, "kek"));
        assertEquals(ClaimStatus.denied(new ClaimDenied().setReason("kek")),
                ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
    }

    @Test
    public void testCreateRequestReviewAndRequestChanges() {
        Claim claim = ServiceUtils.createClaim(client, conversionWrapperService, "party_id", 5);
        ServiceUtils.runService(() -> client.requestClaimReview("party_id", claim.getId(), 0));
        assertEquals(ClaimStatus.review(new ClaimReview()),
                ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
        ServiceUtils.runService(() -> client.requestClaimChanges("party_id", claim.getId(), 1));
        assertEquals(ClaimStatus.pending(new ClaimPending()),
                ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
    }

    @Test
    public void testSearchClaims() {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId("claim_search_party_id");
        claimSearchQuery.setLimit(20);

        List<Claim> claims = ServiceUtils.createClaims(client, conversionWrapperService, claimSearchQuery.getPartyId(),
                claimSearchQuery.getLimit(), 5);
        assertEquals(claims.size(),
                ServiceUtils.callService(() -> client.searchClaims(claimSearchQuery)).getResult().size());

        claimSearchQuery.setStatuses(Arrays.asList(ClaimStatus.pending(new ClaimPending())));
        assertEquals(
                claims.stream().filter(claim -> claimSearchQuery.getStatuses().contains(claim.getStatus())).count(),
                ServiceUtils.callService(() -> client.searchClaims(claimSearchQuery)).getResult().size());

        claimSearchQuery.setStatuses(
                Arrays.asList(ClaimStatus.review(new ClaimReview()), ClaimStatus.accepted(new ClaimAccepted())));
        assertEquals(
                claims.stream().filter(claim -> claimSearchQuery.getStatuses().contains(claim.getStatus())).count(),
                ServiceUtils.callService(() -> client.searchClaims(claimSearchQuery)).getResult().size());
    }

    @Test
    public void testSearchClaimsByClaimId() {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId("claim_search_party_id_and_claim_id");
        claimSearchQuery.setLimit(1);

        Claim claim = ServiceUtils.createClaims(client, conversionWrapperService, claimSearchQuery.getPartyId(),
                claimSearchQuery.getLimit(), 5).get(0);
        claimSearchQuery.setClaimId(claim.getId());

        assertEquals(claim, ServiceUtils.callService(() -> client.searchClaims(claimSearchQuery).getResult().get(0)));
    }

    @Test
    public void testSearchClaimsWithContinuation() {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId("claim_search_party_id_with_continuation");
        claimSearchQuery.setLimit(1);

        ServiceUtils.createClaims(client, conversionWrapperService, claimSearchQuery.getPartyId(), 20, 1);

        List<Claim> searchedClaimList = new ArrayList<>();
        ClaimSearchResponse claimSearchResponse;
        do {
            claimSearchResponse = ServiceUtils.callService(() -> client.searchClaims(claimSearchQuery));
            assertEquals(claimSearchQuery.getLimit(), claimSearchResponse.getResult().size());
            searchedClaimList.addAll(claimSearchResponse.getResult());
            claimSearchQuery.setContinuationToken(claimSearchResponse.getContinuationToken());
        } while (claimSearchResponse.isSetContinuationToken());
        assertEquals(ServiceUtils.callService(() -> client.searchClaims(claimSearchQuery.setLimit(20))).getResult(),
                searchedClaimList);
    }

    @Test
    public void setAndGetMetadata() {
        Claim claim = ServiceUtils.callService(() ->
                client.createClaim("party_id", ServiceUtils.generateModifications(conversionWrapperService, 5)));

        Value value = MockUtil.generateTBase(Value.class);
        ServiceUtils.runService(() -> client.setMetadata("party_id", claim.getId(), "key", value));
        Value value2 = MockUtil.generateTBase(Value.class);
        ServiceUtils.runService(() -> client.setMetadata("party_id", claim.getId(), "key", value2));
        assertEquals(value2, ServiceUtils.callService(() -> client.getMetadata("party_id", claim.getId(), "key")));
    }

    @Test
    public void setAndGetMetadataError() throws TException {
        Claim claim = ServiceUtils.createClaim(client, "party_id",
                ServiceUtils.generateModifications(conversionWrapperService,
                        () -> MockUtil.generateTBaseList(Modification.party_modification(new PartyModification()), 5)));
        assertEquals(claim, ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId())));
        try {
            Mockito.when(kafkaTemplate.send(any(), any())).thenThrow(new RuntimeException());
            ServiceUtils.runService(() -> client.updateClaim("party_id", claim.getId(), 0,
                    ServiceUtils.generateModifications(conversionWrapperService, () -> MockUtil
                            .generateTBaseList(Modification.claim_modification(new ClaimModification()), 5))));
        } catch (Exception e) {
            assertEquals(5, ServiceUtils.callService(() ->
                    client.getClaim("party_id", claim.getId())).getChangesetSize());
            Mockito.verify(kafkaTemplate, Mockito.times(4)).send(any(), any());
        }
    }

    @Test(expected = MetadataKeyNotFound.class)
    public void removeMetadataTest() {
        Claim claim = ServiceUtils.createClaim(client, conversionWrapperService, "party_id", 5);
        Value value = MockUtil.generateTBase(Value.class);
        ServiceUtils.runService(() -> client.setMetadata("party_id", claim.getId(), "key", value));
        assertEquals(value, ServiceUtils.callService(() -> client.getMetadata("party_id", claim.getId(), "key")));
        ServiceUtils.runService(() -> client.removeMetadata("party_id", claim.getId(), "key"));
        ServiceUtils.callService(() -> client.getMetadata("party_id", claim.getId(), "key"));
    }

    @Test
    public void softRemoveModificationTest() {
        Claim claim = ServiceUtils.createClaim(client, conversionWrapperService, "party_id", 5);
        long modificationId = claim.changeset.get(0).getModificationId();
        ServiceUtils.runService(() ->
                client.removeModification("party_id", claim.getId(), claim.getRevision(), modificationId));
        Claim claimWithRemovedMod = ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId()));
        assertEquals(4, claimWithRemovedMod.getChangeset().size());
    }

    @Test(expected = ModificationWrongType.class)
    public void updateBadTypeModificationTest() {

        FileModificationUnit fileModificationUnit = new FileModificationUnit();
        fileModificationUnit.setId("testId");
        FileModification fileModification = new FileModification();
        fileModification.setChanged(new FileChanged());
        fileModificationUnit.setModification(fileModification);
        ClaimModificationChange claimModificationChange = new ClaimModificationChange();
        claimModificationChange.setFileModification(fileModificationUnit);

        Modification commentModification = buildCommentModification();
        Claim claim = ServiceUtils.createClaim(
                client,
                "party_id",
                ServiceUtils.generateModifications(
                        conversionWrapperService,
                        () -> MockUtil.generateTBaseList(commentModification, 1)
                )
        );
        ModificationUnit modificationUnit = claim.changeset.get(0);
        ModificationChange modificationChange = ModificationChange.claim_modification(claimModificationChange);
        ServiceUtils.runService(() -> client.updateModification("party_id", claim.getId(), claim.getRevision(),
                modificationUnit.getModificationId(), modificationChange));
    }

    @Test
    public void updateClaimModificationTest() {

        CommentModificationUnit commentModificationUnit = new CommentModificationUnit();
        commentModificationUnit.setId("testModId");
        CommentModification cmntModification = new CommentModification();
        cmntModification.setChanged(new CommentChanged());
        commentModificationUnit.setModification(cmntModification);
        ClaimModificationChange claimModificationChange = new ClaimModificationChange();
        claimModificationChange.setCommentModification(commentModificationUnit);

        ModificationChange modificationChange = ModificationChange.claim_modification(claimModificationChange);

        Modification commentModification = buildCommentModification();
        Claim claim = ServiceUtils.createClaim(
                client,
                "party_id",
                ServiceUtils.generateModifications(
                        conversionWrapperService,
                        () -> MockUtil.generateTBaseList(commentModification, 1)
                )
        );
        ModificationUnit modificationUnit = claim.changeset.get(0);
        ServiceUtils.runService(() -> client.updateModification("party_id", claim.getId(), claim.getRevision(),
                modificationUnit.getModificationId(), modificationChange));

        Claim modifiedClaim = ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId()));
        ModificationUnit changedModificationUnit = modifiedClaim.getChangeset().get(0);
        Assert.assertEquals(commentModificationUnit.getId(),
                changedModificationUnit.getModification().getClaimModification().getCommentModification().getId());
        Assert.assertTrue(changedModificationUnit.getModification().getClaimModification().getCommentModification()
                .getModification().isSetChanged());
        Assert.assertNotNull(changedModificationUnit.getChangedAt());
    }

    @Test
    public void updateDocClaimModificationTest() {

        DocumentModificationUnit documentModificationUnit = new DocumentModificationUnit();
        documentModificationUnit.setId("testDocId");
        DocumentModification documentModification = new DocumentModification();
        DocumentChanged documentChanged = new DocumentChanged();
        documentModification.setChanged(documentChanged);
        documentModificationUnit.setModification(documentModification);
        ClaimModificationChange documentClaimModificationChange = new ClaimModificationChange();
        documentClaimModificationChange.setDocumentModification(documentModificationUnit);

        ModificationChange docModificationChange =
                ModificationChange.claim_modification(documentClaimModificationChange);

        Modification docModification = buildDocumentModification();
        Claim claim = ServiceUtils.createClaim(
                client,
                "party_id",
                ServiceUtils.generateModifications(
                        conversionWrapperService,
                        () -> MockUtil.generateTBaseList(docModification, 1)
                )
        );
        ModificationUnit modificationUnit = claim.changeset.get(0);
        ServiceUtils.runService(() -> client.updateModification("party_id", claim.getId(), claim.getRevision(),
                modificationUnit.getModificationId(), docModificationChange));

        Claim modifiedClaim = ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId()));
        List<ModificationUnit> changeset = modifiedClaim.getChangeset();
        ModificationUnit docModificationUnit = changeset.get(0);
        Assert.assertEquals(documentModificationUnit.getId(),
                docModificationUnit.getModification().getClaimModification().getDocumentModification().getId());
        Assert.assertTrue(
                docModificationUnit.getModification().getClaimModification().getDocumentModification().getModification()
                        .isSetChanged());
        Assert.assertNotNull(docModificationUnit.getChangedAt());
    }

    @Test
    public void updatePartyModificationTest() {

        ShopLocation shopLocation = new ShopLocation();
        shopLocation.setUrl("newTestLocationUrl");
        ShopParams shopParams = buildShopParams();
        shopParams.setLocation(shopLocation);
        ShopModification shopModificationThrift = new ShopModification();
        shopModificationThrift.setCreation(shopParams);
        ShopModificationUnit shopModificationUnit = new ShopModificationUnit();
        shopModificationUnit.setId("testShopModId");
        shopModificationUnit.setModification(shopModificationThrift);
        PartyModificationChange partyModificationChange = new PartyModificationChange();
        partyModificationChange.setShopModification(shopModificationUnit);

        Modification shopModification = buildShopModification();
        Claim claim = ServiceUtils.createClaim(
                client,
                "party_id",
                ServiceUtils.generateModifications(
                        conversionWrapperService,
                        () -> MockUtil.generateTBaseList(shopModification, 1)
                )
        );
        ModificationUnit modificationUnit = claim.changeset.get(0);
        ModificationChange modificationChange = ModificationChange.party_modification(partyModificationChange);
        ServiceUtils.runService(() -> client.updateModification("party_id", claim.getId(), claim.getRevision(),
                modificationUnit.getModificationId(), modificationChange));

        Claim modifiedClaim = ServiceUtils.callService(() -> client.getClaim("party_id", claim.getId()));
        ModificationUnit changedModificationUnit = modifiedClaim.getChangeset().get(0);
        ShopModificationUnit shopModificationChanged =
                changedModificationUnit.getModification().getPartyModification().getShopModification();
        Assert.assertEquals(shopLocation.getUrl(),
                shopModificationChanged.getModification().getCreation().getLocation().getUrl());
    }

    private Modification buildDocumentModification() {
        DocumentModificationUnit documentModificationUnit = new DocumentModificationUnit();
        documentModificationUnit.setId("testDocId");
        DocumentModification documentModification = new DocumentModification();
        DocumentChanged documentChanged = new DocumentChanged();
        documentModification.setChanged(documentChanged);
        documentModificationUnit.setModification(documentModification);
        ClaimModification docModification = new ClaimModification();
        docModification.setDocumentModification(documentModificationUnit);
        return Modification.claim_modification(docModification);
    }

    private Modification buildCommentModification() {
        CommentModificationUnit commentModificationUnit = new CommentModificationUnit();
        commentModificationUnit.setId("12345");
        CommentModification commentModification = new CommentModification();
        commentModification.setCreation(new CommentCreated());
        commentModificationUnit.setModification(commentModification);
        ClaimModification claimModification = new ClaimModification();
        claimModification.setCommentModification(commentModificationUnit);

        return Modification.claim_modification(claimModification);
    }

    private Modification buildShopModification() {
        ShopModificationUnit shopModificationUnit = new ShopModificationUnit();
        shopModificationUnit.setId("testShopModId");
        ShopModification shopModification = new ShopModification();
        shopModification.setCreation(buildShopParams());
        shopModificationUnit.setModification(shopModification);
        PartyModification partyModification = new PartyModification();
        partyModification.setShopModification(shopModificationUnit);

        return Modification.party_modification(partyModification);
    }

    private ShopParams buildShopParams() {
        ShopParams shopParams = new ShopParams();
        shopParams.setCategory(new CategoryRef(1));
        shopParams.setContractId("testContractId");
        ShopDetails shopDetails = new ShopDetails();
        shopDetails.setName("testNameDetail");
        shopDetails.setDescription("testDescription");
        shopParams.setDetails(shopDetails);
        ShopLocation shopLocation = new ShopLocation();
        shopLocation.setUrl("testUrl");
        shopParams.setLocation(shopLocation);
        shopParams.setPayoutToolId("testPayoutToolId");

        return shopParams;
    }

}
