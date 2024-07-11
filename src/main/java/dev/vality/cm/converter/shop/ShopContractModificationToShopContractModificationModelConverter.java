package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopContractModificationModel;
import dev.vality.damsel.claim_management.ShopContractModification;
import org.springframework.stereotype.Component;

@Component
public class ShopContractModificationToShopContractModificationModelConverter
        implements ClaimConverter<ShopContractModification, ShopContractModificationModel> {

    @Override
    public ShopContractModificationModel convert(ShopContractModification shopContractModification) {
        ShopContractModificationModel shopContractModificationModel = new ShopContractModificationModel();
        shopContractModificationModel.setContractId(shopContractModification.getContractId());
        shopContractModificationModel.setPayoutToolId(shopContractModification.getPayoutToolId());
        return shopContractModificationModel;
    }

}
