package dev.vality.cm.converter;

import dev.vality.cm.model.PayoutToolInfoInternationalBankAccountModel;
import dev.vality.damsel.domain.InternationalBankAccount;
import dev.vality.damsel.domain.PayoutToolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoInternationalBankAccountModelToPayoutToolInfoConverter
        implements ClaimConverter<PayoutToolInfoInternationalBankAccountModel, PayoutToolInfo> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolInfo convert(
            PayoutToolInfoInternationalBankAccountModel payoutToolInfoInternationalBankAccountModel) {
        return PayoutToolInfo.international_bank_account(
                conversionService
                        .convert(payoutToolInfoInternationalBankAccountModel.getInternationalBankAccountModel(),
                                InternationalBankAccount.class)
        );
    }
}
