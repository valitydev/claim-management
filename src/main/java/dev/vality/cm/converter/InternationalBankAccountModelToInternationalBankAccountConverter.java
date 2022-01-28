package dev.vality.cm.converter;

import dev.vality.cm.model.InternationalBankAccountModel;
import dev.vality.damsel.domain.InternationalBankAccount;
import dev.vality.damsel.domain.InternationalBankDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InternationalBankAccountModelToInternationalBankAccountConverter
        implements ClaimConverter<InternationalBankAccountModel, InternationalBankAccount> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public InternationalBankAccount convert(InternationalBankAccountModel internationalBankAccountModel) {
        return new InternationalBankAccount()
                .setIban(internationalBankAccountModel.getIban())
                .setNumber(internationalBankAccountModel.getNumber())
                .setAccountHolder(internationalBankAccountModel.getAccountHolder())
                .setBank(
                        Optional.ofNullable(internationalBankAccountModel.getInternationalBankDetails())
                                .map(internationalBankDetailsModel -> conversionService
                                        .convert(internationalBankDetailsModel, InternationalBankDetails.class))
                                .orElse(null)
                )
                .setCorrespondentAccount(
                        Optional.ofNullable(internationalBankAccountModel.getCorrespondentAccount())
                                .map(correspondentAccount -> conversionService
                                        .convert(correspondentAccount, InternationalBankAccount.class))
                                .orElse(null)
                );
    }
}
