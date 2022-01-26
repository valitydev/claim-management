package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopDetailsModel;
import dev.vality.damsel.domain.ShopDetails;
import org.springframework.stereotype.Component;

@Component
public class ShopDetailsToShopDetailsModelConverter implements ClaimConverter<ShopDetails, ShopDetailsModel> {
    @Override
    public ShopDetailsModel convert(ShopDetails shopDetails) {
        ShopDetailsModel shopDetailsModel = new ShopDetailsModel();
        shopDetailsModel.setName(shopDetails.getName());
        shopDetailsModel.setDescription(shopDetails.getDescription());
        return shopDetailsModel;
    }
}
