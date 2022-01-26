package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopAccountParamsModel;
import dev.vality.cm.model.WalletAccountParamsModel;
import dev.vality.damsel.claim_management.ShopAccountParams;
import dev.vality.damsel.claim_management.WalletAccountParams;
import dev.vality.damsel.domain.CurrencyRef;
import org.springframework.stereotype.Component;

@Component
public class WalletAccountParamsModelToWalletAccountParamsConverter
        implements ClaimConverter<WalletAccountParamsModel, WalletAccountParams> {

    @Override
    public WalletAccountParams convert(WalletAccountParamsModel walletAccountParamsModel) {
        return new WalletAccountParams()
                .setCurrency(new CurrencyRef(walletAccountParamsModel.getCurrencySymbolicCode()));
    }

}
