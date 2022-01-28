package dev.vality.cm.converter.wallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.wallet.WalletModificationModel;
import dev.vality.damsel.claim_management.PartyModification;
import dev.vality.damsel.claim_management.WalletModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class WalletModificationModelToPartyModificationConverter
        implements ClaimConverter<WalletModificationModel, PartyModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModification convert(WalletModificationModel walletModificationModel) {
        return PartyModification.wallet_modification(
                conversionService.convert(walletModificationModel, WalletModificationUnit.class)
        );
    }
}
