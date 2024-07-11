package dev.vality.cm.converter;

import dev.vality.cm.model.PayoutToolInfoPaymentInstitutionAccountModel;
import dev.vality.damsel.domain.PaymentInstitutionAccount;
import org.springframework.stereotype.Component;

@Component
public class PaymentInstitutionAccountToPayoutToolInfoPaymentInstitutionAccountModelConverter
        implements ClaimConverter<PaymentInstitutionAccount, PayoutToolInfoPaymentInstitutionAccountModel> {

    @Override
    public PayoutToolInfoPaymentInstitutionAccountModel convert(PaymentInstitutionAccount paymentInstitutionAccount) {
        PayoutToolInfoPaymentInstitutionAccountModel payoutToolInfoRussianBankAccountModel =
                new PayoutToolInfoPaymentInstitutionAccountModel();
        return payoutToolInfoRussianBankAccountModel;
    }
}
