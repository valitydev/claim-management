package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopParamsModel;
import dev.vality.damsel.claim_management.ShopParams;
import dev.vality.damsel.domain.CategoryRef;
import dev.vality.damsel.domain.ShopDetails;
import dev.vality.damsel.domain.ShopLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopParamsModelToShopParamsConverter implements ClaimConverter<ShopParamsModel, ShopParams> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopParams convert(ShopParamsModel shopParamsModel) {
        return new ShopParams()
                .setContractId(shopParamsModel.getContractId())
                .setCategory(new CategoryRef(shopParamsModel.getCategoryId()))
                .setDetails(conversionService.convert(shopParamsModel.getDetails(), ShopDetails.class))
                .setLocation(conversionService.convert(shopParamsModel.getLocation(), ShopLocation.class));
    }
}
