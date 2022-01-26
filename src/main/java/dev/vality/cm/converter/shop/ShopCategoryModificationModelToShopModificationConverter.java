package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopCategoryModificationModel;
import dev.vality.damsel.claim_management.ShopModification;
import dev.vality.damsel.domain.CategoryRef;
import org.springframework.stereotype.Component;

@Component
public class ShopCategoryModificationModelToShopModificationConverter
        implements ClaimConverter<ShopCategoryModificationModel, ShopModification> {

    @Override
    public ShopModification convert(ShopCategoryModificationModel shopCategoryModificationModel) {
        return ShopModification.category_modification(
                new CategoryRef(shopCategoryModificationModel.getShopCategoryId())
        );
    }

}
