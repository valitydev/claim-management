package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractPayoutToolChangeModificationModel;
import dev.vality.damsel.claim_management.PayoutToolModification;
import dev.vality.damsel.domain.PayoutToolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractPayoutToolChangeModificationModelToPayoutToolModificationConverter
        implements ClaimConverter<ContractPayoutToolChangeModificationModel, PayoutToolModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolModification convert(
            ContractPayoutToolChangeModificationModel contractPayoutToolChangeModificationModel) {
        return PayoutToolModification.info_modification(
                conversionService.convert(contractPayoutToolChangeModificationModel.getPayoutToolInfoModel(),
                        PayoutToolInfo.class)
        );
    }
}
