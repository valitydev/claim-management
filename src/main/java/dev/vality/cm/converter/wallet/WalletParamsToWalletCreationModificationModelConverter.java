package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.WalletParamsModel;
import dev.vality.cm.model.wallet.WalletCreationModificationModel;
import dev.vality.damsel.claim_management.WalletParams;
import org.springframework.stereotype.Component;

@Component
public class WalletParamsToWalletCreationModificationModelConverter
        implements ClaimConverter<WalletParams, WalletCreationModificationModel> {

    @Override
    public WalletCreationModificationModel convert(WalletParams walletParams) {
        WalletParamsModel walletParamsModel = new WalletParamsModel();
        walletParamsModel.setName(walletParams.getName());
        walletParamsModel.setContractId(walletParams.getContractId());
        WalletCreationModificationModel wallletCreationModification = new WalletCreationModificationModel();
        wallletCreationModification.setWalletParams(walletParamsModel);
        return wallletCreationModification;
    }
}
