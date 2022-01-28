package dev.vality.cm.converter;

import dev.vality.cm.model.RussianBankAccountModel;
import dev.vality.damsel.domain.RussianBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RussianBankAccountToRussianBankAccountModelConverter
        implements ClaimConverter<RussianBankAccount, RussianBankAccountModel> {
    @Override
    public RussianBankAccountModel convert(RussianBankAccount russianBankAccount) {
        RussianBankAccountModel russianBankAccountModel = new RussianBankAccountModel();
        russianBankAccountModel.setAccount(russianBankAccount.getAccount());
        russianBankAccountModel.setBankPostAccount(russianBankAccount.getBankPostAccount());
        russianBankAccountModel.setBankBik(russianBankAccount.getBankBik());
        russianBankAccountModel.setBankName(russianBankAccount.getBankName());
        return russianBankAccountModel;
    }
}
