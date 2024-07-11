package dev.vality.cm.converter.additional.info;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.AdditionalInfoModificationModel;
import dev.vality.damsel.claim_management.AdditionalInfoModificationUnit;
import dev.vality.damsel.claim_management.PartyModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class AdditionalInfoModificationModelToPartyModificationConverter
        implements ClaimConverter<AdditionalInfoModificationModel, PartyModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;


    @Override
    public PartyModification convert(AdditionalInfoModificationModel source) {
        PartyModification partyModification = new PartyModification();
        partyModification.setAdditionalInfoModification(
                conversionService.convert(source, AdditionalInfoModificationUnit.class)
        );
        return partyModification;
    }
}
