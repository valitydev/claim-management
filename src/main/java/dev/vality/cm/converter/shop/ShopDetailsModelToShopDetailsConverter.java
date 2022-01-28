package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopDetailsModel;
import dev.vality.damsel.domain.ShopDetails;
import org.springframework.stereotype.Component;

@Component
public class ShopDetailsModelToShopDetailsConverter implements ClaimConverter<ShopDetailsModel, ShopDetails> {
    @Override
    public ShopDetails convert(ShopDetailsModel shopDetailsModel) {
        return new ShopDetails()
                .setName(shopDetailsModel.getName())
                .setDescription(shopDetailsModel.getDescription());
    }
}
