package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopAccountParamsModel;
import dev.vality.damsel.claim_management.ShopAccountParams;
import org.springframework.stereotype.Component;

@Component
public class ShopAccountParamsToShopAccountParamsModelConverter
        implements ClaimConverter<ShopAccountParams, ShopAccountParamsModel> {
    @Override
    public ShopAccountParamsModel convert(ShopAccountParams shopAccountParams) {
        ShopAccountParamsModel shopAccountParamsModel = new ShopAccountParamsModel();
        shopAccountParamsModel.setCurrencySymbolicCode(shopAccountParams.getCurrency().getSymbolicCode());
        return shopAccountParamsModel;
    }
}
