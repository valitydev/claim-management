package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.WalletAccountParamsModel;
import dev.vality.damsel.claim_management.WalletAccountParams;
import org.springframework.stereotype.Component;

@Component
public class WalletAccountParamsToWalletAccountParamsModelConverter
        implements ClaimConverter<WalletAccountParams, WalletAccountParamsModel> {
    @Override
    public WalletAccountParamsModel convert(WalletAccountParams shopAccountParams) {
        WalletAccountParamsModel walletAccountParamsModel = new WalletAccountParamsModel();
        walletAccountParamsModel.setCurrencySymbolicCode(shopAccountParams.getCurrency().getSymbolicCode());
        return walletAccountParamsModel;
    }
}
