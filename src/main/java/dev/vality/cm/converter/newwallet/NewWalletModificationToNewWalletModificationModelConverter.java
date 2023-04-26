package dev.vality.cm.converter.newwallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.newwallet.NewWalletCreationModificationModel;
import dev.vality.cm.model.newwallet.NewWalletModificationModel;
import dev.vality.damsel.claim_management.NewWalletModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class NewWalletModificationToNewWalletModificationModelConverter
        implements ClaimConverter<NewWalletModification, NewWalletModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public NewWalletModificationModel convert(NewWalletModification newWalletModification) {
        if (newWalletModification.getSetField() == NewWalletModification._Fields.CREATION) {
            return conversionService.convert(
                    newWalletModification.getCreation(),
                    NewWalletCreationModificationModel.class);
        }
        throw new IllegalArgumentException(String.format("Unknown type '%s'", newWalletModification.getSetField()));
    }
}
