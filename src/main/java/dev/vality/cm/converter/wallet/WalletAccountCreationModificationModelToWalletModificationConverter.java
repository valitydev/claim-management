package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.wallet.WalletAccountCreationModificationModel;
import dev.vality.damsel.claim_management.WalletAccountParams;
import dev.vality.damsel.claim_management.WalletModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class WalletAccountCreationModificationModelToWalletModificationConverter
        implements ClaimConverter<WalletAccountCreationModificationModel, WalletModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public WalletModification convert(WalletAccountCreationModificationModel walletAccountCreationModificationModel) {
        return WalletModification.account_creation(
                conversionService
                        .convert(walletAccountCreationModificationModel.getWalletAccountParams(),
                                WalletAccountParams.class)
        );
    }
}
