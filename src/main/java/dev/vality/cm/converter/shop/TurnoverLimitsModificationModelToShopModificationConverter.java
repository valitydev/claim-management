package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.TurnoverLimitsModificationModel;
import dev.vality.damsel.claim_management.ShopModification;
import dev.vality.damsel.domain.TurnoverLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TurnoverLimitsModificationModelToShopModificationConverter
        implements ClaimConverter<TurnoverLimitsModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(TurnoverLimitsModificationModel turnoverLimitsModificationModel) {
        Set<TurnoverLimit> turnoverLimits = turnoverLimitsModificationModel.getLimits().stream()
                .map(turnoverLimitModel -> conversionService.convert(turnoverLimitModel, TurnoverLimit.class))
                .collect(Collectors.toSet());
        return ShopModification.turnover_limits_modification(turnoverLimits);
    }
}
