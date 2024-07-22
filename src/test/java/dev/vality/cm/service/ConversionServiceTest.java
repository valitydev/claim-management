package dev.vality.cm.service;

import dev.vality.cm.config.ConverterConfig;
import dev.vality.cm.model.ClaimModel;
import dev.vality.cm.model.ClaimModificationModel;
import dev.vality.cm.model.PartyModificationModel;
import dev.vality.cm.model.comment.CommentModificationModel;
import dev.vality.cm.model.contract.ContractModificationModel;
import dev.vality.cm.model.contractor.ContractorModificationModel;
import dev.vality.cm.model.document.DocumentModificationModel;
import dev.vality.cm.model.external.info.ExternalInfoModificationModel;
import dev.vality.cm.model.file.FileModificationModel;
import dev.vality.cm.model.shop.ShopModificationModel;
import dev.vality.cm.model.status.StatusModificationModel;
import dev.vality.cm.util.FilterUtils;
import dev.vality.cm.util.MockUtil;
import dev.vality.damsel.claim_management.*;
import dev.vality.geck.common.util.TypeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConverterConfig.class, ConversionService.class})
public class ConversionServiceTest {

    @Autowired
    private ConversionService conversionService;

    @Test
    @Repeat(10)
    public void testCommentModificationConverters() {
        CommentModificationUnit commentModificationUnit = MockUtil.generateTBase(CommentModificationUnit.class);

        assertEquals(
                commentModificationUnit,
                conversionService.convert(
                        conversionService.convert(commentModificationUnit, CommentModificationModel.class),
                        ClaimModification.class
                ).getCommentModification()
        );
    }

    @Test
    @Repeat(10)
    public void testFileModificationConverters() {
        FileModificationUnit fileModificationUnit = MockUtil.generateTBase(FileModificationUnit.class);

        assertEquals(
                fileModificationUnit,
                conversionService.convert(
                        conversionService.convert(fileModificationUnit, FileModificationModel.class),
                        ClaimModification.class
                ).getFileModification()
        );
    }

    @Test
    @Repeat(10)
    public void testDocumentModificationConverters() {
        DocumentModificationUnit documentModificationUnit = MockUtil.generateTBase(DocumentModificationUnit.class);
        documentModificationUnit.setType(null);
        assertEquals(
                documentModificationUnit,
                conversionService.convert(
                        conversionService.convert(documentModificationUnit, DocumentModificationModel.class),
                        ClaimModification.class
                ).getDocumentModification()
        );
    }

    @Test
    @Repeat(10)
    public void testStatusModificationConverters() {
        StatusModificationUnit statusModificationUnit = MockUtil.generateTBase(StatusModificationUnit.class);

        assertEquals(
                statusModificationUnit,
                conversionService.convert(
                        conversionService.convert(statusModificationUnit, StatusModificationModel.class),
                        ClaimModification.class
                ).getStatusModification()
        );
    }

    @Test
    @Repeat(10)
    public void testExternalInfoModificationConverters() {
        ExternalInfoModificationUnit externalInfoModificationUnit =
                MockUtil.generateTBase(ExternalInfoModificationUnit.class);
        externalInfoModificationUnit.setDocumentId("123");
        assertEquals(
                externalInfoModificationUnit,
                conversionService.convert(
                        conversionService.convert(externalInfoModificationUnit, ExternalInfoModificationModel.class),
                        ClaimModification.class
                ).getExternalInfoModification()
        );
    }

    @Test
    @Repeat(10)
    public void testClaimModificationConverters() {
        ClaimModification claimModification = MockUtil.generateTBase(ClaimModification.class);
        if (claimModification.isSetDocumentModification()) {
            claimModification.getDocumentModification().setType(null);
        }
        assertEquals(
                claimModification,
                conversionService.convert(
                        conversionService.convert(claimModification, ClaimModificationModel.class),
                        ClaimModification.class
                )
        );
    }

    @Test
    @Repeat(10)
    public void testShopModificationConverters() {
        ShopModification shopModification = MockUtil.generateTBase(ShopModification.class);
        if (!FilterUtils.isUnusedModification(shopModification)) { // TODO remove after aa new modification
            assertEquals(
                    shopModification,
                    conversionService.convert(
                            conversionService.convert(shopModification, ShopModificationModel.class),
                            ShopModification.class
                    )
            );
        }
    }



    @Test
    @Repeat(10)
    public void testContractModificationConverters() {
        ContractModification contractModification = MockUtil.generateTBase(ContractModification.class);
        assertEquals(
                contractModification,
                conversionService.convert(
                        conversionService.convert(contractModification, ContractModificationModel.class),
                        ContractModification.class
                )
        );
    }

    @Test
    @Repeat(10)
    public void testIdentityModificationConverters() {
        IdentityModification identityModification = MockUtil.generateTBase(IdentityModification.class);
        assertEquals(
                identityModification,
                conversionService.convert(
                        conversionService.convert(identityModification, IdentityModification.class),
                        IdentityModification.class
                )
        );
    }

    @Test
    @Repeat(10)
    public void testNewWalletModificationConverters() {
        NewWalletModification newWalletModification = MockUtil.generateTBase(NewWalletModification.class);
        assertEquals(
                newWalletModification,
                conversionService.convert(
                        conversionService.convert(newWalletModification, NewWalletModification.class),
                        NewWalletModification.class
                )
        );
    }

    @Test
    @Repeat(10)
    public void testContractorModificationConverters() {
        ContractorModification contractorModification = MockUtil.generateTBase(ContractorModification.class);
        if (!FilterUtils.isUnusedModification(contractorModification)) { // TODO remove after aa new modification
            assertEquals(
                    contractorModification,
                    conversionService.convert(
                            conversionService.convert(contractorModification, ContractorModificationModel.class),
                            ContractorModification.class
                    )
            );
        }
    }

    @Test
    @Repeat(10)
    public void testAdditionalInfoModificationConverters() {
        AdditionalInfoModificationUnit additionalInfoModification =
                MockUtil.generateTBase(AdditionalInfoModificationUnit.class);
        assertEquals(
                additionalInfoModification,
                conversionService.convert(
                        conversionService.convert(additionalInfoModification, AdditionalInfoModificationUnit.class),
                        AdditionalInfoModificationUnit.class
                )
        );
    }

    @Test
    @Repeat(10)
    public void testPartyModificationConverters() {
        PartyModification partyModification = MockUtil.generateTBase(PartyModification.class);
        if (!FilterUtils.isUnusedModification(partyModification)) { // TODO remove after aa new modification
            assertEquals(
                    partyModification,
                    conversionService.convert(
                            conversionService.convert(partyModification, PartyModificationModel.class),
                            PartyModification.class
                    )
            );
        }
    }



    @Test
    @Repeat(20)
    public void testAllClaimConverters() {
        Claim claim = MockUtil.generateTBase(Claim.class);
        claim.getChangeset().stream().forEach(changeset -> {
            if (changeset.getModification().isSetClaimModification()
                    && changeset.getModification().getClaimModification().isSetDocumentModification()) {
                changeset.getModification().getClaimModification().getDocumentModification().setType(null);
            }
        });
        if (CollectionUtils.isEmpty(claim.getMetadata())) {
            claim.setMetadata(null);
        }
        List<ModificationUnit> modificationUnits = claim.getChangeset().stream()
                .peek(mod -> {
                    mod.setChangedAt(TypeUtil.temporalToString(Instant.now().truncatedTo(ChronoUnit.MICROS)));
                    mod.setRemovedAt(null);
                })
                .toList();
        List<ModificationUnit> filterModificationUnits = modificationUnits.stream()
                // TODO remove after add new modification
                .filter(modificationUnit -> !FilterUtils.isUnusedModification(modificationUnit))
                .toList();
        claim.setChangeset(filterModificationUnits);
        assertEquals(
                claim,
                conversionService.convert(
                        conversionService.convert(claim, ClaimModel.class),
                        Claim.class
                )
        );
    }
}
