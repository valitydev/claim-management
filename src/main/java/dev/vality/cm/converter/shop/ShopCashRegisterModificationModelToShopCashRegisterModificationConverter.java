package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopCashRegisterCreationModificationModel;
import dev.vality.cm.model.shop.ShopCashRegisterModificationModel;
import dev.vality.damsel.claim_management.CashRegisterModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCashRegisterModificationModelToShopCashRegisterModificationConverter
        implements ClaimConverter<CashRegisterModification, ShopCashRegisterModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopCashRegisterModificationModel convert(CashRegisterModification cashRegisterModification) {
        switch (cashRegisterModification.getSetField()) {
            case CREATION:
                return conversionService.convert(cashRegisterModification.getCreation(),
                        ShopCashRegisterCreationModificationModel.class);
            default:
                throw new IllegalArgumentException(
                        "Unknown cashRegisterModification type: " + cashRegisterModification.getSetField());
        }
    }

}
