package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.WalletAccountParamsModel;
import dev.vality.cm.model.wallet.WalletAccountCreationModificationModel;
import dev.vality.damsel.claim_management.WalletAccountParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class WalletAccountParamsToWalletAccountCreationModificationModelConverter
        implements ClaimConverter<WalletAccountParams, WalletAccountCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public WalletAccountCreationModificationModel convert(WalletAccountParams walletAccountParams) {
        WalletAccountCreationModificationModel walletAccountCreationModificationModel =
                new WalletAccountCreationModificationModel();
        walletAccountCreationModificationModel
                .setWalletAccountParams(conversionService.convert(walletAccountParams, WalletAccountParamsModel.class));
        return walletAccountCreationModificationModel;
    }
}
