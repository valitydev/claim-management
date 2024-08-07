package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.TurnoverLimitModificationModel;
import dev.vality.cm.model.shop.TurnoverLimitsModificationModel;
import dev.vality.damsel.domain.TurnoverLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TurnoverLimitsToTurnoverLimitsModificationModelConverter implements
        ClaimConverter<Set<TurnoverLimit>, TurnoverLimitsModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public TurnoverLimitsModificationModel convert(Set<TurnoverLimit> turnoverLimits) {
        TurnoverLimitsModificationModel limitsModificationModel = new TurnoverLimitsModificationModel();
        Set<TurnoverLimitModificationModel> limits = turnoverLimits.stream()
                .map(turnoverLimit -> conversionService.convert(turnoverLimit, TurnoverLimitModificationModel.class))
                .collect(Collectors.toSet());
        limitsModificationModel.setLimits(limits);
        return limitsModificationModel;
    }
}
