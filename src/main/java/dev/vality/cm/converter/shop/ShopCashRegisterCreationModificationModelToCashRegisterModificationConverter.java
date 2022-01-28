package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopCashRegisterCreationModificationModel;
import dev.vality.damsel.claim_management.CashRegisterModification;
import dev.vality.damsel.claim_management.CashRegisterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCashRegisterCreationModificationModelToCashRegisterModificationConverter
        implements ClaimConverter<ShopCashRegisterCreationModificationModel, CashRegisterModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public CashRegisterModification convert(
            ShopCashRegisterCreationModificationModel shopCashRegisterCreationModificationModel) {
        CashRegisterModification cashRegisterModification = new CashRegisterModification();
        cashRegisterModification.setCreation(
                conversionService.convert(
                        shopCashRegisterCreationModificationModel.getCashRegisterParamsModel(),
                        CashRegisterParams.class
                )
        );

        return cashRegisterModification;
    }

}
