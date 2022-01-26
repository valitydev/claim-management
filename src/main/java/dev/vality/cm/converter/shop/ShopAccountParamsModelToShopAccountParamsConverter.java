package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopAccountParamsModel;
import dev.vality.damsel.claim_management.ShopAccountParams;
import dev.vality.damsel.domain.CurrencyRef;
import org.springframework.stereotype.Component;

@Component
public class ShopAccountParamsModelToShopAccountParamsConverter
        implements ClaimConverter<ShopAccountParamsModel, ShopAccountParams> {

    @Override
    public ShopAccountParams convert(ShopAccountParamsModel shopAccountParamsModel) {
        return new ShopAccountParams()
                .setCurrency(new CurrencyRef(shopAccountParamsModel.getCurrencySymbolicCode()));
    }

}
