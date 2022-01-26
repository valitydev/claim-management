package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopPayoutScheduleModificationModel;
import dev.vality.damsel.claim_management.ScheduleModification;
import dev.vality.damsel.claim_management.ShopModification;
import dev.vality.damsel.domain.BusinessScheduleRef;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShopPayoutScheduleModificationModelToShopModificationConverter
        implements ClaimConverter<ShopPayoutScheduleModificationModel, ShopModification> {

    @Override
    public ShopModification convert(ShopPayoutScheduleModificationModel shopPayoutScheduleModificationModel) {
        return ShopModification.payout_schedule_modification(
                new ScheduleModification()
                        .setSchedule(
                                Optional.ofNullable(shopPayoutScheduleModificationModel.getBusinessScheduleId())
                                        .map(BusinessScheduleRef::new)
                                        .orElse(null)
                        )
        );
    }

}
