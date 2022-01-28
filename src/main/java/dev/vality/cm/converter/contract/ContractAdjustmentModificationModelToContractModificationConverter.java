package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractAdjustmentModificationModel;
import dev.vality.damsel.claim_management.ContractAdjustmentModificationUnit;
import dev.vality.damsel.claim_management.ContractModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentModificationModelToContractModificationConverter
        implements ClaimConverter<ContractAdjustmentModificationModel, ContractModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModification convert(ContractAdjustmentModificationModel contractAdjustmentModificationModel) {
        return ContractModification.adjustment_modification(
                conversionService.convert(contractAdjustmentModificationModel, ContractAdjustmentModificationUnit.class)
        );
    }
}
