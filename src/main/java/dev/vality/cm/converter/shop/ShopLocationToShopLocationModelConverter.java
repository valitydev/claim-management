package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopLocationModel;
import dev.vality.cm.model.ShopUrlLocationModel;
import dev.vality.damsel.domain.ShopLocation;
import org.springframework.stereotype.Component;

@Component
public class ShopLocationToShopLocationModelConverter implements ClaimConverter<ShopLocation, ShopLocationModel> {

    @Override
    public ShopLocationModel convert(ShopLocation shopLocation) {
        switch (shopLocation.getSetField()) {
            case URL:
                ShopUrlLocationModel shopUrlLocationModel = new ShopUrlLocationModel();
                shopUrlLocationModel.setUrl(shopLocation.getUrl());
                return shopUrlLocationModel;
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", shopLocation.getSetField()));
        }
    }
}
