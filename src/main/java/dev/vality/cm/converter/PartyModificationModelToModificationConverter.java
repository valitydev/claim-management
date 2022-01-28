package dev.vality.cm.converter;

import dev.vality.cm.model.PartyModificationModel;
import dev.vality.damsel.claim_management.Modification;
import dev.vality.damsel.claim_management.PartyModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PartyModificationModelToModificationConverter
        implements ClaimConverter<PartyModificationModel, Modification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Modification convert(PartyModificationModel partyModificationModel) {
        return Modification.party_modification(
                conversionService.convert(
                        partyModificationModel,
                        PartyModification.class
                )
        );
    }
}
