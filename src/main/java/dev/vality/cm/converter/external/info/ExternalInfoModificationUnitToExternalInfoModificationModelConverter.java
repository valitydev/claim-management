package dev.vality.cm.converter.external.info;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.external.info.ExternalInfoModificationModel;
import dev.vality.damsel.claim_management.ExternalInfoModificationUnit;
import org.springframework.stereotype.Component;

@Component
public class ExternalInfoModificationUnitToExternalInfoModificationModelConverter
        implements ClaimConverter<ExternalInfoModificationUnit, ExternalInfoModificationModel> {

    @Override
    public ExternalInfoModificationModel convert(ExternalInfoModificationUnit externalInfoModificationUnit) {
        ExternalInfoModificationModel model = new ExternalInfoModificationModel();
        model.setDocumentId(externalInfoModificationUnit.getDocumentId());
        model.setRoistatId(externalInfoModificationUnit.getRoistatId());
        return model;
    }
}
