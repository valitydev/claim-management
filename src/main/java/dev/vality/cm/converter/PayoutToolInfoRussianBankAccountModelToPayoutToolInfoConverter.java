package dev.vality.cm.converter;

import dev.vality.cm.model.PayoutToolInfoRussianBankAccountModel;
import dev.vality.damsel.domain.PayoutToolInfo;
import dev.vality.damsel.domain.RussianBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoRussianBankAccountModelToPayoutToolInfoConverter
        implements ClaimConverter<PayoutToolInfoRussianBankAccountModel, PayoutToolInfo> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolInfo convert(PayoutToolInfoRussianBankAccountModel payoutToolInfoRussianBankAccountModel) {
        return PayoutToolInfo.russian_bank_account(
                conversionService.convert(payoutToolInfoRussianBankAccountModel.getRussianBankAccount(),
                        RussianBankAccount.class)
        );
    }
}
