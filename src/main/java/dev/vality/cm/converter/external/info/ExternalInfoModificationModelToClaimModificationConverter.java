package dev.vality.cm.converter.external.info;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.external.info.ExternalInfoModificationModel;
import dev.vality.damsel.claim_management.ClaimModification;
import dev.vality.damsel.claim_management.ExternalInfoModificationUnit;
import org.springframework.stereotype.Component;

@Component
public class ExternalInfoModificationModelToClaimModificationConverter
        implements ClaimConverter<ExternalInfoModificationModel, ClaimModification> {

    @Override
    public ClaimModification convert(ExternalInfoModificationModel externalInfoModificationModel) {
        return ClaimModification.external_info_modification(
                new ExternalInfoModificationUnit()
                        .setDocumentId(externalInfoModificationModel.getDocumentId())
                        .setRoistatId(externalInfoModificationModel.getRoistatId())
        );
    }
}
