package dev.vality.cm.converter.status;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ClaimStatusModel;
import dev.vality.cm.model.status.StatusModificationModel;
import dev.vality.cm.model.status.StatusModificationTypeEnum;
import dev.vality.damsel.claim_management.StatusModificationUnit;
import dev.vality.geck.common.util.TBaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class StatusModificationUnitToStatusModificationModelConverter
        implements ClaimConverter<StatusModificationUnit, StatusModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public StatusModificationModel convert(StatusModificationUnit statusModificationUnit) {
        StatusModificationModel statusModificationModel = new StatusModificationModel();
        statusModificationModel.setClaimStatus(
                conversionService.convert(statusModificationUnit.getStatus(), ClaimStatusModel.class)
        );
        statusModificationModel.setStatusModificationType(
                TBaseUtil.unionFieldToEnum(
                        statusModificationUnit.getModification(),
                        StatusModificationTypeEnum.class
                )
        );
        return statusModificationModel;
    }
}
