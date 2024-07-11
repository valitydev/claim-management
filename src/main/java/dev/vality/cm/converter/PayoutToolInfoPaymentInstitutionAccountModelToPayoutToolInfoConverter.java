package dev.vality.cm.converter;

import dev.vality.cm.model.PayoutToolInfoPaymentInstitutionAccountModel;
import dev.vality.damsel.domain.PaymentInstitutionAccount;
import dev.vality.damsel.domain.PayoutToolInfo;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoPaymentInstitutionAccountModelToPayoutToolInfoConverter
        implements ClaimConverter<PayoutToolInfoPaymentInstitutionAccountModel, PayoutToolInfo> {

    @Override
    public PayoutToolInfo convert(PayoutToolInfoPaymentInstitutionAccountModel paymentInstitutionAccountModel) {
        return PayoutToolInfo.payment_institution_account(new PaymentInstitutionAccount());
    }
}
