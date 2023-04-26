package dev.vality.cm.converter.newwallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.newwallet.NewWalletModificationModel;
import dev.vality.damsel.claim_management.NewWalletModification;
import dev.vality.damsel.claim_management.NewWalletModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class NewWalletModificationModelToNewWalletModificationUnitConverter
        implements ClaimConverter<NewWalletModificationModel, NewWalletModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public NewWalletModificationUnit convert(NewWalletModificationModel newWalletModificationModel) {
        return new NewWalletModificationUnit()
                .setId(newWalletModificationModel.getNewWalletId())
                .setModification(conversionService.convert(
                        newWalletModificationModel,
                        NewWalletModification.class));
    }
}
