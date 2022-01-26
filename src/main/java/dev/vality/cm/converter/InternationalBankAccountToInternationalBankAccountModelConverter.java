package dev.vality.cm.converter;

import dev.vality.cm.model.InternationalBankAccountModel;
import dev.vality.cm.model.InternationalBankDetailsModel;
import dev.vality.damsel.domain.InternationalBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class InternationalBankAccountToInternationalBankAccountModelConverter
        implements ClaimConverter<InternationalBankAccount, InternationalBankAccountModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public InternationalBankAccountModel convert(InternationalBankAccount internationalBankAccount) {
        InternationalBankAccountModel internationalBankAccountModel = new InternationalBankAccountModel();
        internationalBankAccountModel.setAccountHolder(internationalBankAccount.getAccountHolder());
        internationalBankAccountModel.setIban(internationalBankAccount.getIban());
        internationalBankAccountModel.setNumber(internationalBankAccount.getNumber());
        if (internationalBankAccount.isSetBank()) {
            internationalBankAccountModel.setInternationalBankDetails(
                    conversionService.convert(internationalBankAccount.getBank(), InternationalBankDetailsModel.class));
        }
        if (internationalBankAccount.isSetCorrespondentAccount()) {
            internationalBankAccountModel.setCorrespondentAccount(conversionService
                    .convert(internationalBankAccount.getCorrespondentAccount(), InternationalBankAccountModel.class));
        }
        return internationalBankAccountModel;
    }
}
