package dev.vality.cm.converter;

import dev.vality.cm.model.PayoutToolInfoWalletInfoModel;
import dev.vality.damsel.domain.PayoutToolInfo;
import dev.vality.damsel.domain.WalletInfo;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoWalletInfoModelToPayoutToolInfoConverter
        implements ClaimConverter<PayoutToolInfoWalletInfoModel, PayoutToolInfo> {

    @Override
    public PayoutToolInfo convert(PayoutToolInfoWalletInfoModel payoutToolInfoWalletInfoModel) {
        return PayoutToolInfo.wallet_info(
                new WalletInfo(payoutToolInfoWalletInfoModel.getWalletId())
        );
    }
}
