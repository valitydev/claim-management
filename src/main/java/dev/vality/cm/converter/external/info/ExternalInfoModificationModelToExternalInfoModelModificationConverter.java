package dev.vality.cm.converter.external.info;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.external.info.ExternalInfoModificationModel;
import dev.vality.damsel.claim_management.ExternalInfoModificationUnit;
import org.springframework.stereotype.Component;

@Component
public class ExternalInfoModificationModelToExternalInfoModelModificationConverter
        implements ClaimConverter<ExternalInfoModificationModel, ExternalInfoModificationUnit> {

    @Override
    public ExternalInfoModificationUnit convert(ExternalInfoModificationModel externalInfoModificationModel) {
        return new ExternalInfoModificationUnit()
                .setDocumentId(externalInfoModificationModel.getDocumentId())
                .setRoistatId(externalInfoModificationModel.getRoistatId());
    }

}
