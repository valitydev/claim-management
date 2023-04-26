package dev.vality.cm.converter.identity;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.damsel.claim_management.IdentityModificationUnit;
import dev.vality.damsel.claim_management.Modification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class IdentityModificationToModificationConverter
        implements ClaimConverter<IdentityModificationUnit, Modification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Modification convert(IdentityModificationUnit identityModificationUnit) {
        return Modification.identity_modification(
                conversionService.convert(
                        identityModificationUnit,
                        IdentityModificationUnit.class));
    }
}
