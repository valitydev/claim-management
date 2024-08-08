package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.TurnoverLimitModificationModel;
import dev.vality.damsel.domain.TurnoverLimit;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TurnoverLimitModelToTurnoverLimitConverter
        implements ClaimConverter<TurnoverLimitModificationModel, TurnoverLimit> {

    @Override
    public TurnoverLimit convert(TurnoverLimitModificationModel turnoverLimitModificationModel) {
        TurnoverLimit turnoverLimit = new TurnoverLimit();
        turnoverLimit.setId(turnoverLimitModificationModel.getLimitConfigId());
        turnoverLimit.setUpperBoundary(turnoverLimitModificationModel.getAmountUpperBoundary());
        if (Objects.nonNull(turnoverLimitModificationModel.getDomainRevision())) {
            turnoverLimit.setDomainRevision(turnoverLimitModificationModel.getDomainRevision());
        }
        return turnoverLimit;
    }
}
