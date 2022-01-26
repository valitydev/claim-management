package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractAdjustmentCreationModificationModel;
import dev.vality.damsel.claim_management.ContractAdjustmentModification;
import dev.vality.damsel.claim_management.ContractAdjustmentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentCreationModificationModelToContractAdjustmentModificationConverter
        implements ClaimConverter<ContractAdjustmentCreationModificationModel, ContractAdjustmentModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractAdjustmentModification convert(
            ContractAdjustmentCreationModificationModel contractAdjustmentCreationModificationModel) {
        return ContractAdjustmentModification.creation(
                conversionService.convert(contractAdjustmentCreationModificationModel.getContractAdjustmentParams(),
                        ContractAdjustmentParams.class)
        );
    }
}
