package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopDetailsModel;
import dev.vality.cm.model.shop.ShopDetailsModificationModel;
import dev.vality.damsel.domain.ShopDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopDetailsToShopDetailsModificationModelConverter
        implements ClaimConverter<ShopDetails, ShopDetailsModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopDetailsModificationModel convert(ShopDetails shopDetails) {
        ShopDetailsModificationModel shopDetailsModificationModel = new ShopDetailsModificationModel();
        shopDetailsModificationModel.setDetails(conversionService.convert(shopDetails, ShopDetailsModel.class));
        return shopDetailsModificationModel;
    }
}
