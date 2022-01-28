package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.WalletParamsModel;
import dev.vality.damsel.claim_management.WalletParams;
import org.springframework.stereotype.Component;

@Component
public class WalletParamsModelToWalletParamsConverter implements ClaimConverter<WalletParamsModel, WalletParams> {

    @Override
    public WalletParams convert(WalletParamsModel walletParamsModel) {
        return new WalletParams()
                .setContractId(walletParamsModel.getContractId())
                .setName(walletParamsModel.getName());
    }
}
