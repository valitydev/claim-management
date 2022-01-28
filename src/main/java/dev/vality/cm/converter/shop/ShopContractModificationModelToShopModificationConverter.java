package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopContractModificationModel;
import dev.vality.damsel.claim_management.ShopContractModification;
import dev.vality.damsel.claim_management.ShopModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopContractModificationModelToShopModificationConverter
        implements ClaimConverter<ShopContractModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopContractModificationModel shopContractModificationModel) {
        return ShopModification.contract_modification(
                conversionService.convert(shopContractModificationModel, ShopContractModification.class)
        );
    }
}
