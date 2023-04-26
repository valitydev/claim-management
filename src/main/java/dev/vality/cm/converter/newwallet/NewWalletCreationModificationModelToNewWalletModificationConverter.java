package dev.vality.cm.converter.newwallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.newwallet.NewWalletCreationModificationModel;
import dev.vality.damsel.claim_management.NewWalletModification;
import dev.vality.damsel.claim_management.NewWalletParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class NewWalletCreationModificationModelToNewWalletModificationConverter
        implements ClaimConverter<NewWalletCreationModificationModel, NewWalletModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public NewWalletModification convert(NewWalletCreationModificationModel newWalletCreationModificationModel) {
        return NewWalletModification.creation(
                conversionService.convert(
                        newWalletCreationModificationModel.getNewWalletParams(),
                        NewWalletParams.class));
    }
}
