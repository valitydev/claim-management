package dev.vality.cm.converter.additional.info;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.AdditionalInfoModificationModel;
import dev.vality.damsel.claim_management.AdditionalInfoModificationUnit;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AdditionalInfoModificationModelToAdditionalInfoModificationUnitConverter
        implements ClaimConverter<AdditionalInfoModificationModel, AdditionalInfoModificationUnit> {

    @Override
    public AdditionalInfoModificationUnit convert(AdditionalInfoModificationModel source) {
        AdditionalInfoModificationUnit additionalInfoModificationUnit = new AdditionalInfoModificationUnit();
        additionalInfoModificationUnit.setComment(source.getComment());
        additionalInfoModificationUnit.setPartyName(source.getPartyName());
        additionalInfoModificationUnit.setManagerContactEmails(
                source.getManagerContactEmails() != null
                        ? Arrays.stream(source.getManagerContactEmails().split(", "))
                        .toList()
                        : null);
        return additionalInfoModificationUnit;
    }
}

