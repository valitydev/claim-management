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
public class NewWalletModificationUnitToNewWalletModificationModelConverter
        implements ClaimConverter<NewWalletModificationUnit, NewWalletModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public NewWalletModificationModel convert(NewWalletModificationUnit newWalletModificationUnit) {
        NewWalletModification newWalletModification = newWalletModificationUnit.getModification();
        NewWalletModificationModel newWalletModificationModel =
                conversionService.convert(newWalletModification, NewWalletModificationModel.class);
        if (newWalletModificationModel == null) {
            throw new IllegalStateException("NewWalletModificationModel can't be null");
        }
        newWalletModificationModel.setNewWalletId(newWalletModificationUnit.getId());
        return newWalletModificationModel;
    }
}
