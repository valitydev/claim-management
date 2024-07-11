package dev.vality.cm.converter.shop;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.ShopPayoutScheduleModificationModel;
import dev.vality.damsel.claim_management.ScheduleModification;
import org.springframework.stereotype.Component;

@Component
public class ScheduleModificationToShopPayoutScheduleModificationModelConverter
        implements ClaimConverter<ScheduleModification, ShopPayoutScheduleModificationModel> {
    @Override
    public ShopPayoutScheduleModificationModel convert(ScheduleModification scheduleModification) {
        ShopPayoutScheduleModificationModel shopPayoutScheduleModificationModel =
                new ShopPayoutScheduleModificationModel();
        if (scheduleModification.isSetSchedule()) {
            shopPayoutScheduleModificationModel.setBusinessScheduleId(scheduleModification.getSchedule().getId());
        }
        return shopPayoutScheduleModificationModel;
    }
}
