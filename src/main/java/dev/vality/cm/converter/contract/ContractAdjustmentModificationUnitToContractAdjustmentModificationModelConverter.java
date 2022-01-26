package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractAdjustmentModificationModel;
import dev.vality.damsel.claim_management.ContractAdjustmentModification;
import dev.vality.damsel.claim_management.ContractAdjustmentModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentModificationUnitToContractAdjustmentModificationModelConverter
        implements ClaimConverter<ContractAdjustmentModificationUnit, ContractAdjustmentModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractAdjustmentModificationModel convert(
            ContractAdjustmentModificationUnit contractAdjustmentModificationUnit) {
        ContractAdjustmentModification contractAdjustmentModification =
                contractAdjustmentModificationUnit.getModification();
        ContractAdjustmentModificationModel contractAdjustmentModificationModel =
                conversionService.convert(contractAdjustmentModification, ContractAdjustmentModificationModel.class);
        if (contractAdjustmentModificationModel == null) {
            throw new IllegalStateException("ContractAdjustmentModificationModel can't be null");
        }
        contractAdjustmentModificationModel
                .setContractAdjustmentId(contractAdjustmentModificationUnit.getAdjustmentId());
        return contractAdjustmentModificationModel;
    }
}
