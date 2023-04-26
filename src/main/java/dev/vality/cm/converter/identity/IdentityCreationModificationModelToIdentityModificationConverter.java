package dev.vality.cm.converter.identity;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.identity.IdentityCreationModificationModel;
import dev.vality.damsel.claim_management.IdentityModification;
import dev.vality.damsel.claim_management.IdentityParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class IdentityCreationModificationModelToIdentityModificationConverter
        implements ClaimConverter<IdentityCreationModificationModel, IdentityModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public IdentityModification convert(IdentityCreationModificationModel identityCreationModificationModel) {
        return IdentityModification.creation(
                conversionService.convert(
                        identityCreationModificationModel.getIdentityParams(),
                        IdentityParams.class));
    }
}
