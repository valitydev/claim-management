package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopLocationModel;
import dev.vality.cm.model.shop.ShopLocationModificationModel;
import dev.vality.damsel.domain.ShopLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopLocationToShopLocationModificationModelConverter
        implements ClaimConverter<ShopLocation, ShopLocationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopLocationModificationModel convert(ShopLocation shopLocation) {
        ShopLocationModificationModel shopLocationModificationModel = new ShopLocationModificationModel();
        shopLocationModificationModel.setLocation(conversionService.convert(shopLocation, ShopLocationModel.class));
        return shopLocationModificationModel;
    }
}
