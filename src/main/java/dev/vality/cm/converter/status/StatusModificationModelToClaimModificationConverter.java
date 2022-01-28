package dev.vality.cm.converter.status;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.status.StatusModificationModel;
import dev.vality.damsel.claim_management.ClaimModification;
import dev.vality.damsel.claim_management.ClaimStatus;
import dev.vality.damsel.claim_management.StatusModification;
import dev.vality.damsel.claim_management.StatusModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class StatusModificationModelToClaimModificationConverter
        implements ClaimConverter<StatusModificationModel, ClaimModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModification convert(StatusModificationModel statusModificationModel) {
        ClaimStatus claimStatus =
                conversionService.convert(statusModificationModel.getClaimStatus(), ClaimStatus.class);
        StatusModification statusModification =
                conversionService.convert(statusModificationModel, StatusModification.class);
        return ClaimModification.status_modification(
                new StatusModificationUnit()
                        .setStatus(claimStatus)
                        .setModification(statusModification)
        );
    }
}
