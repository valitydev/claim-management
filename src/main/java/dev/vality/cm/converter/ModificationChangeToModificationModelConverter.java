package dev.vality.cm.converter;

import dev.vality.cm.model.ClaimModificationModel;
import dev.vality.cm.model.ModificationModel;
import dev.vality.cm.model.PartyModificationModel;
import dev.vality.cm.model.identity.IdentityModificationModel;
import dev.vality.cm.model.newwallet.NewWalletModificationModel;
import dev.vality.damsel.claim_management.ModificationChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ModificationChangeToModificationModelConverter
        implements ClaimConverter<ModificationChange, ModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ModificationModel convert(ModificationChange modificationChange) {
        switch (modificationChange.getSetField()) {
            case CLAIM_MODIFICATION:
                return conversionService
                        .convert(modificationChange.getClaimModification(), ClaimModificationModel.class);
            case PARTY_MODIFICATION:
                return conversionService
                        .convert(modificationChange.getPartyModification(), PartyModificationModel.class);
            case IDENTITY_MODIFICATION:
                return conversionService
                        .convert(modificationChange.getPartyModification(), IdentityModificationModel.class);
            case WALLET_MODIFICATION:
                return conversionService
                        .convert(modificationChange.getPartyModification(), NewWalletModificationModel.class);
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", modificationChange.getSetField()));
        }
    }
}
