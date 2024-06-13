package dev.vality.cm.converter;

import dev.vality.cm.model.AdditionalInfoModificationModel;
import dev.vality.cm.model.PartyModificationModel;
import dev.vality.cm.model.contract.ContractModificationModel;
import dev.vality.cm.model.contractor.ContractorModificationModel;
import dev.vality.cm.model.shop.ShopModificationModel;
import dev.vality.cm.model.wallet.WalletModificationModel;
import dev.vality.damsel.claim_management.PartyModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PartyModificationToPartyModificationModelConverter
        implements ClaimConverter<PartyModification, PartyModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModificationModel convert(PartyModification partyModification) {
        return switch (partyModification.getSetField()) {
            case SHOP_MODIFICATION -> conversionService
                    .convert(partyModification.getShopModification(), ShopModificationModel.class);
            case CONTRACT_MODIFICATION -> conversionService
                    .convert(partyModification.getContractModification(), ContractModificationModel.class);
            case CONTRACTOR_MODIFICATION -> conversionService
                    .convert(partyModification.getContractorModification(), ContractorModificationModel.class);
            case WALLET_MODIFICATION -> conversionService
                    .convert(partyModification.getWalletModification(), WalletModificationModel.class);
            case ADDITIONAL_INFO_MODIFICATION -> conversionService
                    .convert(partyModification.getAdditionalInfoModification(), AdditionalInfoModificationModel.class);
            default -> throw new IllegalArgumentException(
                    String.format("Unknown type '%s'", partyModification.getSetField()));
        };
    }
}
