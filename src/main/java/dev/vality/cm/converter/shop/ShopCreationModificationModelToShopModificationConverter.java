package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopCreationModificationModel;
import dev.vality.damsel.claim_management.ShopModification;
import dev.vality.damsel.claim_management.ShopParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCreationModificationModelToShopModificationConverter
        implements ClaimConverter<ShopCreationModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopCreationModificationModel shopCreationModificationModel) {
        return ShopModification.creation(
                conversionService.convert(shopCreationModificationModel.getShopParams(), ShopParams.class)
        );
    }
}
