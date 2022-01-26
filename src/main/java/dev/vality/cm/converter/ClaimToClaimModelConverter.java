package dev.vality.cm.converter;

import dev.vality.cm.model.ClaimModel;
import dev.vality.cm.model.ClaimStatusModel;
import dev.vality.cm.model.MetadataModel;
import dev.vality.cm.model.ModificationModel;
import dev.vality.damsel.claim_management.Claim;
import dev.vality.geck.common.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClaimToClaimModelConverter implements ClaimConverter<Claim, ClaimModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModel convert(Claim claim) {
        ClaimModel claimModel = new ClaimModel();
        claimModel.setId(claim.getId());
        claimModel.setPartyId(claim.getPartyId());
        claimModel.setCreatedAt(TypeUtil.stringToInstant(claim.getCreatedAt()));
        claimModel.setRevision(claim.getRevision());
        claimModel.setClaimStatus(conversionService.convert(claim.getStatus(), ClaimStatusModel.class));
        claimModel.setModifications(
                claim.getChangeset().stream()
                        .map(modificationUnit -> conversionService.convert(modificationUnit, ModificationModel.class))
                        .collect(Collectors.toList())
        );

        if (claim.isSetUpdatedAt()) {
            claimModel.setUpdatedAt(TypeUtil.stringToInstant(claim.getUpdatedAt()));
        }
        if (claim.isSetMetadata()) {
            claimModel.setMetadata(
                    claim.getMetadata().entrySet().stream()
                            .map(entry -> conversionService.convert(entry, MetadataModel.class))
                            .collect(Collectors.toList())
            );
        }
        return claimModel;
    }
}
