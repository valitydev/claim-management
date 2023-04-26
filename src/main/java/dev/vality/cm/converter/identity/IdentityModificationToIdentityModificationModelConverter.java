package dev.vality.cm.converter.identity;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.identity.IdentityCreationModificationModel;
import dev.vality.cm.model.identity.IdentityModificationModel;
import dev.vality.damsel.claim_management.IdentityModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class IdentityModificationToIdentityModificationModelConverter
        implements ClaimConverter<IdentityModification, IdentityModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public IdentityModificationModel convert(IdentityModification identityModification) {
        if (identityModification.getSetField() == IdentityModification._Fields.CREATION) {
            return conversionService.convert(
                    identityModification.getCreation(),
                    IdentityCreationModificationModel.class);
        }
        throw new IllegalArgumentException(String.format("Unknown type '%s'", identityModification.getSetField()));
    }
}
