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
public class WalletModificationUnitToWalletModificationModelConverter
        implements ClaimConverter<WalletModificationUnit, WalletModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public WalletModificationModel convert(WalletModificationUnit walletModificationUnit) {
        WalletModification walletModification = walletModificationUnit.getModification();
        WalletModificationModel walletModificationModel =
                conversionService.convert(walletModification, WalletModificationModel.class);
        if (walletModificationModel == null) {
            throw new IllegalStateException("WalletModificationModel can't be null");
        }
        walletModificationModel.setWalletId(walletModificationUnit.getId());
        return walletModificationModel;
    }
}
