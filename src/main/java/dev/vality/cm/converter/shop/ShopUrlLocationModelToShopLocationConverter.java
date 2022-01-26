package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopUrlLocationModel;
import dev.vality.damsel.domain.ShopLocation;
import org.springframework.stereotype.Component;

@Component
public class ShopUrlLocationModelToShopLocationConverter implements ClaimConverter<ShopUrlLocationModel, ShopLocation> {
    @Override
    public ShopLocation convert(ShopUrlLocationModel shopUrlLocationModel) {
        return ShopLocation.url(shopUrlLocationModel.getUrl());
    }
}
