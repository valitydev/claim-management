package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopModificationModel;
import dev.vality.damsel.claim_management.ShopModification;
import dev.vality.damsel.claim_management.ShopModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopModificationUnitToShopModificationModelConverter
        implements ClaimConverter<ShopModificationUnit, ShopModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModificationModel convert(ShopModificationUnit shopModificationUnit) {
        ShopModification shopModification = shopModificationUnit.getModification();
        ShopModificationModel shopModificationModel =
                conversionService.convert(shopModification, ShopModificationModel.class);
        if (shopModificationModel == null) {
            throw new IllegalStateException("ShopModificationModel can't be null");
        }
        shopModificationModel.setShopId(shopModificationUnit.getId());
        return shopModificationModel;
    }
}
