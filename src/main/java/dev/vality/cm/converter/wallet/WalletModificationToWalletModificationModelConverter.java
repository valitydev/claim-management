package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.wallet.*;
import dev.vality.damsel.claim_management.WalletModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class WalletModificationToWalletModificationModelConverter
        implements ClaimConverter<WalletModification, WalletModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public WalletModificationModel convert(WalletModification walletModification) {
        return switch (walletModification.getSetField()) {
            case CREATION -> conversionService.convert(walletModification.getCreation(),
                    WalletCreationModificationModel.class);
            case ACCOUNT_CREATION -> conversionService
                    .convert(walletModification.getAccountCreation(),
                            WalletAccountCreationModificationModel.class);
            default -> throw new IllegalArgumentException(
                    String.format("Unknown type '%s'", walletModification.getSetField()));
        };
    }
}
