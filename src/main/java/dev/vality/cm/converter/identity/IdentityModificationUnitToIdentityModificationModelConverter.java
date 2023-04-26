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
public class IdentityModificationUnitToIdentityModificationModelConverter
        implements ClaimConverter<IdentityModificationUnit, IdentityModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public IdentityModificationModel convert(IdentityModificationUnit identityModificationUnit) {
        IdentityModification identityModification = identityModificationUnit.getModification();
        IdentityModificationModel identityModificationModel =
                conversionService.convert(identityModification, IdentityModificationModel.class);
        if (identityModificationModel == null) {
            throw new IllegalStateException("IdentityModificationModel can't be null");
        }
        identityModificationModel.setIdentityId(identityModificationUnit.getId());
        return identityModificationModel;
    }
}
