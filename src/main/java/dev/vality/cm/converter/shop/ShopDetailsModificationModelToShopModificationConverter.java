package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopDetailsModificationModel;
import dev.vality.damsel.claim_management.ShopModification;
import dev.vality.damsel.domain.ShopDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopDetailsModificationModelToShopModificationConverter
        implements ClaimConverter<ShopDetailsModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopDetailsModificationModel shopDetailsModificationModel) {
        return ShopModification.details_modification(
                conversionService.convert(shopDetailsModificationModel.getDetails(), ShopDetails.class)
        );
    }
}
