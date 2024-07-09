package dev.vality.cm.converter.additional.info;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.AdditionalInfoModificationModel;
import dev.vality.damsel.claim_management.AdditionalInfoModificationUnit;
import org.springframework.stereotype.Component;

@Component
public class AdditionalInfoModificationUnitToAdditionalInfoModificationModelConverter
        implements ClaimConverter<AdditionalInfoModificationUnit, AdditionalInfoModificationModel> {

    @Override
    public AdditionalInfoModificationModel convert(AdditionalInfoModificationUnit additionalInfoModificationUnit) {
        AdditionalInfoModificationModel additionalInfoModificationModel = new AdditionalInfoModificationModel();
        additionalInfoModificationModel.setComment(additionalInfoModificationUnit.getComment());
        additionalInfoModificationModel.setPartyName(additionalInfoModificationUnit.getPartyName());
        additionalInfoModificationModel.setManagerContactEmails(
                additionalInfoModificationUnit.getManagerContactEmails() != null
                        ? String.join(", ", additionalInfoModificationUnit.getManagerContactEmails())
                        : null);
        return additionalInfoModificationModel;
    }
}
