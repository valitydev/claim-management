package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ShopDetailsModel;
import dev.vality.cm.model.ShopLocationModel;
import dev.vality.cm.model.ShopParamsModel;
import dev.vality.cm.model.shop.ShopCreationModificationModel;
import dev.vality.damsel.claim_management.ShopParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopParamsToShopCreationModificationModelConverter
        implements ClaimConverter<ShopParams, ShopCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopCreationModificationModel convert(ShopParams shopParams) {
        ShopParamsModel shopParamsModel = new ShopParamsModel();
        shopParamsModel.setCategoryId(shopParams.getCategory().getId());
        shopParamsModel.setContractId(shopParams.getContractId());
        shopParamsModel.setPayoutToolId(shopParams.getPayoutToolId());
        shopParamsModel.setDetails(conversionService.convert(shopParams.getDetails(), ShopDetailsModel.class));
        shopParamsModel.setLocation(conversionService.convert(shopParams.getLocation(), ShopLocationModel.class));
        ShopCreationModificationModel shopCreationModification = new ShopCreationModificationModel();
        shopCreationModification.setShopParams(shopParamsModel);
        return shopCreationModification;
    }
}
