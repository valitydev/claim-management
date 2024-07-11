package dev.vality.cm.converter;

import dev.vality.cm.model.InternationalBankAccountModel;
import dev.vality.cm.model.PayoutToolInfoInternationalBankAccountModel;
import dev.vality.damsel.domain.InternationalBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class InternationalBankAccountToPayoutToolInfoInternationalBankAccountModelConverter
        implements ClaimConverter<InternationalBankAccount, PayoutToolInfoInternationalBankAccountModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolInfoInternationalBankAccountModel convert(InternationalBankAccount internationalBankAccount) {
        PayoutToolInfoInternationalBankAccountModel payoutToolInfoInternationalBankAccountModel =
                new PayoutToolInfoInternationalBankAccountModel();
        payoutToolInfoInternationalBankAccountModel.setInternationalBankAccountModel(
                conversionService.convert(internationalBankAccount, InternationalBankAccountModel.class));
        return payoutToolInfoInternationalBankAccountModel;
    }
}
