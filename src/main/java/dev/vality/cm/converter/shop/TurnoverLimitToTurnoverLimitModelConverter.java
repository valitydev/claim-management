package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.TurnoverLimitModificationModel;
import dev.vality.damsel.domain.TurnoverLimit;
import org.springframework.stereotype.Component;

@Component
public class TurnoverLimitToTurnoverLimitModelConverter implements
        ClaimConverter<TurnoverLimit, TurnoverLimitModificationModel> {

    @Override
    public TurnoverLimitModificationModel convert(TurnoverLimit turnoverLimit) {
        TurnoverLimitModificationModel turnoverLimitModificationModel = new TurnoverLimitModificationModel();
        turnoverLimitModificationModel.setLimitConfigId(turnoverLimit.getId());
        turnoverLimitModificationModel.setAmountUpperBoundary(turnoverLimit.getUpperBoundary());
        if (turnoverLimit.getDomainRevision() > 0) {
            turnoverLimitModificationModel.setDomainRevision(turnoverLimit.getDomainRevision());
        }
        return turnoverLimitModificationModel;
    }
}
