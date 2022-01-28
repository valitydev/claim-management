package dev.vality.cm;

import dev.vality.cm.meta.UserIdentityEmailExtensionKit;
import dev.vality.cm.meta.UserIdentityIdExtensionKit;
import dev.vality.cm.meta.UserIdentityRealmExtensionKit;
import dev.vality.cm.meta.UserIdentityUsernameExtensionKit;
import dev.vality.cm.model.ClaimModel;
import dev.vality.cm.model.ClaimStatusEnum;
import dev.vality.cm.repository.ClaimRepository;
import dev.vality.cm.service.ClaimManagementService;
import dev.vality.cm.service.ConversionWrapperService;
import dev.vality.damsel.claim_management.*;
import dev.vality.geck.common.util.TypeUtil;
import dev.vality.woody.thrift.impl.http.THSpawnClientBuilder;
import dev.vality.cm.util.ServiceUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static dev.vality.cm.util.ServiceUtils.createClaim;
import static dev.vality.cm.util.ServiceUtils.runService;

public class ClaimEventListenerTest extends AbstractKafkaIntegrationTest {

    @Autowired
    private ClaimManagementService claimManagementService;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ConversionWrapperService conversionWrapperService;

    private ClaimManagementSrv.Iface client;

    @Before
    public void setUp() throws Exception {
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
        claimRepository.deleteAll();
    }

    @Test
    public void testClaimStatusChangeHandler() throws InterruptedException, ExecutionException {
        // Given
        final String partyId = UUID.randomUUID().toString();
        Event event = new Event();
        event.setOccuredAt(TypeUtil.temporalToString(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)));
        Change change = new Change();
        event.setChange(change);
        ClaimStatusChanged claimStatusChanged = new ClaimStatusChanged();
        change.setStatusChanged(claimStatusChanged);
        claimStatusChanged.setId(1);
        claimStatusChanged.setPartyId(partyId);
        claimStatusChanged.setStatus(ClaimStatus.pending_acceptance(new ClaimPendingAcceptance()));
        claimStatusChanged.setRevision(0);
        claimStatusChanged.setUpdatedAt(TypeUtil.temporalToString(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)));

        // When
        Claim pendingClaim = ServiceUtils.createClaim(client, conversionWrapperService, partyId, 5);
        ServiceUtils.runService(() -> client.acceptClaim(partyId, pendingClaim.getId(), 0));
        Producer<String, Event> producer = createProducer();
        ProducerRecord<String, Event> producerRecord = new ProducerRecord<>(eventSinkTopic, partyId, event);
        producer.send(producerRecord).get();

        // Then
        Awaitility.await().atMost(60, TimeUnit.SECONDS).pollDelay(Durations.ONE_SECOND).until(() -> {
            ClaimModel claim = claimManagementService.getClaim(partyId, 1);
            return claim != null && claim.getClaimStatus().getClaimStatusEnum() == ClaimStatusEnum.accepted;
        });
    }

}
