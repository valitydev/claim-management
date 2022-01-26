package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopCashRegisterModificationModel;
import dev.vality.damsel.claim_management.CashRegisterModificationUnit;
import dev.vality.damsel.claim_management.ShopModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCashRegisterModificationModelToShopModification
        implements ClaimConverter<ShopCashRegisterModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopCashRegisterModificationModel cashRegisterModificationModel) {
        return ShopModification.cash_register_modification_unit(
                conversionService.convert(cashRegisterModificationModel, CashRegisterModificationUnit.class));
    }

}
