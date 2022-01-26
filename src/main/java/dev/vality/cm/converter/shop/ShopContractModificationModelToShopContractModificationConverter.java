package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopContractModificationModel;
import dev.vality.damsel.claim_management.ShopContractModification;
import org.springframework.stereotype.Component;

@Component
public class ShopContractModificationModelToShopContractModificationConverter
        implements ClaimConverter<ShopContractModificationModel, ShopContractModification> {

    @Override
    public ShopContractModification convert(ShopContractModificationModel shopContractModificationModel) {
        return new ShopContractModification()
                .setContractId(shopContractModificationModel.getContractId())
                .setPayoutToolId(shopContractModificationModel.getPayoutToolId());
    }

}
