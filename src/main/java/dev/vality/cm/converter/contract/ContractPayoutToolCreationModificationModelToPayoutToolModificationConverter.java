package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractPayoutToolCreationModificationModel;
import dev.vality.damsel.claim_management.PayoutToolModification;
import dev.vality.damsel.claim_management.PayoutToolParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractPayoutToolCreationModificationModelToPayoutToolModificationConverter
        implements ClaimConverter<ContractPayoutToolCreationModificationModel, PayoutToolModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolModification convert(
            ContractPayoutToolCreationModificationModel contractPayoutToolCreationModificationModel) {
        return PayoutToolModification.creation(
                conversionService.convert(contractPayoutToolCreationModificationModel.getPayoutToolParams(),
                        PayoutToolParams.class)
        );
    }
}
