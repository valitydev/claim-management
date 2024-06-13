package dev.vality.cm.converter.additional.info;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.AdditionalInfoModificationModel;
import dev.vality.damsel.claim_management.AdditionalInfoModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class AdditionalInfoModificationUnitToWalletModificationModelConverter
        implements ClaimConverter<AdditionalInfoModificationUnit, AdditionalInfoModificationModel> {

    @Override
    public AdditionalInfoModificationModel convert(AdditionalInfoModificationUnit additionalInfoModificationUnit) {
        AdditionalInfoModificationModel additionalInfoModificationModel = new AdditionalInfoModificationModel();
        additionalInfoModificationModel.setComment(additionalInfoModificationModel.getComment());
        additionalInfoModificationModel.setPartyName(additionalInfoModificationModel.getPartyName());
        additionalInfoModificationModel.setManagerContactEmails(
                additionalInfoModificationUnit.getManagerContactEmails());
        return additionalInfoModificationModel;
    }
}
