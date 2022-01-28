package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopCashRegisterModificationModel;
import dev.vality.damsel.claim_management.CashRegisterModification;
import dev.vality.damsel.claim_management.CashRegisterModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCashRegisterModificationModelToShopCashRegisterModificationUnitConverter
        implements ClaimConverter<ShopCashRegisterModificationModel, CashRegisterModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public CashRegisterModificationUnit convert(ShopCashRegisterModificationModel cashRegisterModificationModel) {
        return new CashRegisterModificationUnit()
                .setId(cashRegisterModificationModel.getRegisterId())
                .setModification(
                        conversionService.convert(cashRegisterModificationModel, CashRegisterModification.class)
                );
    }

}
