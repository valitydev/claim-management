package dev.vality.cm.converter.newwallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.damsel.claim_management.Modification;
import dev.vality.damsel.claim_management.NewWalletModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class NewWalletModificationToModificationConverter
        implements ClaimConverter<NewWalletModificationUnit, Modification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Modification convert(NewWalletModificationUnit walletModificationUnit) {
        return Modification.wallet_modification(
                conversionService.convert(walletModificationUnit, NewWalletModificationUnit.class));
    }
}
