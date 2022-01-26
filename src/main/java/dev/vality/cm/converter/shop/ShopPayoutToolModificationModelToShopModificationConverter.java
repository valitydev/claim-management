package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopPayoutToolModificationModel;
import dev.vality.damsel.claim_management.ShopModification;
import org.springframework.stereotype.Component;

@Component
public class ShopPayoutToolModificationModelToShopModificationConverter
        implements ClaimConverter<ShopPayoutToolModificationModel, ShopModification> {

    @Override
    public ShopModification convert(ShopPayoutToolModificationModel shopPayoutToolModificationModel) {
        return ShopModification.payout_tool_modification(shopPayoutToolModificationModel.getPayoutToolId());
    }

}
