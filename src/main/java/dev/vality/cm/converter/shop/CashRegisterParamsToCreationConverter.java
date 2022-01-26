package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.CashRegisterParamsModel;
import dev.vality.cm.model.shop.ShopCashRegisterCreationModificationModel;
import dev.vality.damsel.claim_management.CashRegisterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class CashRegisterParamsToCreationConverter
        implements ClaimConverter<CashRegisterParams, ShopCashRegisterCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopCashRegisterCreationModificationModel convert(CashRegisterParams cashRegisterParams) {
        ShopCashRegisterCreationModificationModel shopCashRegisterCreationModificationModel =
                new ShopCashRegisterCreationModificationModel();
        CashRegisterParamsModel cashRegisterParamsModel =
                conversionService.convert(cashRegisterParams, CashRegisterParamsModel.class);
        shopCashRegisterCreationModificationModel.setCashRegisterParamsModel(cashRegisterParamsModel);

        return shopCashRegisterCreationModificationModel;
    }

}
