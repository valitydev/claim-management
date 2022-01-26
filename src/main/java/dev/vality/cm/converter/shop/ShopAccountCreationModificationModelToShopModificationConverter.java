package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopAccountCreationModificationModel;
import dev.vality.damsel.claim_management.ShopAccountParams;
import dev.vality.damsel.claim_management.ShopModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopAccountCreationModificationModelToShopModificationConverter
        implements ClaimConverter<ShopAccountCreationModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopAccountCreationModificationModel shopAccountCreationModificationModel) {
        return ShopModification.shop_account_creation(
                conversionService
                        .convert(shopAccountCreationModificationModel.getShopAccountParams(), ShopAccountParams.class)
        );
    }
}
