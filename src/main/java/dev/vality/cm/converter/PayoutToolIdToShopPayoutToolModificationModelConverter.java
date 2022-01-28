package dev.vality.cm.converter;

import dev.vality.cm.model.shop.ShopPayoutToolModificationModel;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolIdToShopPayoutToolModificationModelConverter
        implements ClaimConverter<String, ShopPayoutToolModificationModel> {

    @Override
    public ShopPayoutToolModificationModel convert(String payoutToolId) {
        ShopPayoutToolModificationModel shopPayoutToolModificationModel = new ShopPayoutToolModificationModel();
        shopPayoutToolModificationModel.setPayoutToolId(payoutToolId);
        return shopPayoutToolModificationModel;
    }

}
