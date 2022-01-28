package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopLocationModificationModel;
import dev.vality.damsel.claim_management.ShopModification;
import dev.vality.damsel.domain.ShopLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopLocationModificationModelToShopModificationConverter
        implements ClaimConverter<ShopLocationModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopLocationModificationModel shopLocationModificationModel) {
        return ShopModification.location_modification(
                conversionService.convert(shopLocationModificationModel.getLocation(), ShopLocation.class)
        );
    }
}
