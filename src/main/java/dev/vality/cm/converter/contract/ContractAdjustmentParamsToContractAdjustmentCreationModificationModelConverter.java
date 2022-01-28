package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ContractAdjustmentParamsModel;
import dev.vality.cm.model.contract.ContractAdjustmentCreationModificationModel;
import dev.vality.damsel.claim_management.ContractAdjustmentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentParamsToContractAdjustmentCreationModificationModelConverter
        implements ClaimConverter<ContractAdjustmentParams, ContractAdjustmentCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractAdjustmentCreationModificationModel convert(ContractAdjustmentParams contractAdjustmentParams) {
        ContractAdjustmentCreationModificationModel contractAdjustmentCreationModificationModel =
                new ContractAdjustmentCreationModificationModel();
        contractAdjustmentCreationModificationModel.setContractAdjustmentParams(
                conversionService.convert(contractAdjustmentParams, ContractAdjustmentParamsModel.class));
        return contractAdjustmentCreationModificationModel;
    }
}
