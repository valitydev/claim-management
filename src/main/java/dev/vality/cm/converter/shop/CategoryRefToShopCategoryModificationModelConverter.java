package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopCategoryModificationModel;
import dev.vality.damsel.domain.CategoryRef;
import org.springframework.stereotype.Component;

@Component
public class CategoryRefToShopCategoryModificationModelConverter
        implements ClaimConverter<CategoryRef, ShopCategoryModificationModel> {
    @Override
    public ShopCategoryModificationModel convert(CategoryRef categoryRef) {
        ShopCategoryModificationModel shopCategoryModificationModel = new ShopCategoryModificationModel();
        shopCategoryModificationModel.setShopCategoryId(categoryRef.getId());
        return shopCategoryModificationModel;
    }
}
