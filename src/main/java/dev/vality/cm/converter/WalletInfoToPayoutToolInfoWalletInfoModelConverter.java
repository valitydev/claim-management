package dev.vality.cm.converter;

import dev.vality.cm.model.PayoutToolInfoWalletInfoModel;
import dev.vality.damsel.domain.WalletInfo;
import org.springframework.stereotype.Component;

@Component
public class WalletInfoToPayoutToolInfoWalletInfoModelConverter
        implements ClaimConverter<WalletInfo, PayoutToolInfoWalletInfoModel> {

    @Override
    public PayoutToolInfoWalletInfoModel convert(WalletInfo walletInfo) {
        PayoutToolInfoWalletInfoModel contractPayoutToolInfoWalletInfoModel = new PayoutToolInfoWalletInfoModel();
        contractPayoutToolInfoWalletInfoModel.setWalletId(walletInfo.getWalletId());
        return contractPayoutToolInfoWalletInfoModel;
    }

}
