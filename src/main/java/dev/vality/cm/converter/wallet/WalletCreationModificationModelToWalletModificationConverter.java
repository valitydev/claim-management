package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.wallet.WalletCreationModificationModel;
import dev.vality.damsel.claim_management.WalletModification;
import dev.vality.damsel.claim_management.WalletParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class WalletCreationModificationModelToWalletModificationConverter
        implements ClaimConverter<WalletCreationModificationModel, WalletModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public WalletModification convert(WalletCreationModificationModel walletCreationModificationModel) {
        return WalletModification.creation(
                conversionService.convert(walletCreationModificationModel.getWalletParams(), WalletParams.class)
        );
    }
}
