package dev.vality.cm.converter;

import dev.vality.cm.model.ClaimModificationModel;
import dev.vality.cm.model.ModificationModel;
import dev.vality.cm.model.PartyModificationModel;
import dev.vality.damsel.claim_management.Modification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ModificationToModificationModelConverter implements ClaimConverter<Modification, ModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ModificationModel convert(Modification modification) {
        switch (modification.getSetField()) {
            case CLAIM_MODIFICATION:
                return conversionService.convert(modification.getClaimModification(), ClaimModificationModel.class);
            case PARTY_MODIFICATION:
                return conversionService.convert(modification.getPartyModification(), PartyModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", modification.getSetField()));
        }
    }
}
