package dev.vality.cm.converter.identity;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.identity.IdentityModificationModel;
import dev.vality.damsel.claim_management.IdentityModification;
import dev.vality.damsel.claim_management.IdentityModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class IdentityModificationModelToIdentityModificationUnitConverter
        implements ClaimConverter<IdentityModificationModel, IdentityModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public IdentityModificationUnit convert(IdentityModificationModel identityModificationModel) {
        return new IdentityModificationUnit()
                .setId(identityModificationModel.getIdentityId())
                .setModification(
                        conversionService.convert(identityModificationModel, IdentityModification.class));
    }
}
