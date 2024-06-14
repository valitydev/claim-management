package dev.vality.cm.converter;

import dev.vality.cm.model.AdditionalInfoModificationModel;
import dev.vality.cm.model.PartyModificationModel;
import dev.vality.cm.model.contract.ContractModificationModel;
import dev.vality.cm.model.contractor.ContractorModificationModel;
import dev.vality.cm.model.shop.ShopModificationModel;
import dev.vality.damsel.claim_management.PartyModificationChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PartyModificationChangeToPartyModificationModelConverter
        implements ClaimConverter<PartyModificationChange, PartyModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModificationModel convert(PartyModificationChange partyModificationChange) {
        switch (partyModificationChange.getSetField()) {
            case SHOP_MODIFICATION:
                return conversionService
                        .convert(partyModificationChange.getShopModification(), ShopModificationModel.class);
            case CONTRACT_MODIFICATION:
                return conversionService
                        .convert(partyModificationChange.getContractModification(), ContractModificationModel.class);
            case CONTRACTOR_MODIFICATION:
                return conversionService.convert(partyModificationChange.getContractorModification(),
                        ContractorModificationModel.class);
            case ADDITIONAL_INFO_MODIFICATION:
                return conversionService.convert(partyModificationChange.getAdditionalInfoModification(),
                        AdditionalInfoModificationModel.class);
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", partyModificationChange.getSetField()));
        }
    }
}
