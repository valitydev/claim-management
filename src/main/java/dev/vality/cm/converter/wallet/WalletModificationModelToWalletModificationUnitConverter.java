package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.wallet.WalletModificationModel;
import dev.vality.damsel.claim_management.WalletModification;
import dev.vality.damsel.claim_management.WalletModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class WalletModificationModelToWalletModificationUnitConverter
        implements ClaimConverter<WalletModificationModel, WalletModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public WalletModificationUnit convert(WalletModificationModel walletModificationModel) {
        return new WalletModificationUnit()
                .setId(walletModificationModel.getWalletId())
                .setModification(
                        conversionService.convert(walletModificationModel, WalletModification.class)
                );
    }
}
