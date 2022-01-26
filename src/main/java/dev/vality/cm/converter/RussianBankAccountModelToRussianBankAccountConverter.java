package dev.vality.cm.converter;

import dev.vality.cm.model.RussianBankAccountModel;
import dev.vality.damsel.domain.RussianBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RussianBankAccountModelToRussianBankAccountConverter
        implements ClaimConverter<RussianBankAccountModel, RussianBankAccount> {

    @Override
    public RussianBankAccount convert(RussianBankAccountModel russianBankAccountModel) {
        return new RussianBankAccount()
                .setAccount(russianBankAccountModel.getAccount())
                .setBankBik(russianBankAccountModel.getBankBik())
                .setBankName(russianBankAccountModel.getBankName())
                .setBankPostAccount(russianBankAccountModel.getBankPostAccount())
                .setAccount(russianBankAccountModel.getAccount());
    }
}
