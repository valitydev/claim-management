package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ContractAdjustmentParamsModel;
import dev.vality.damsel.claim_management.ContractAdjustmentParams;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentParamsToContractAdjustmentParamsModelConverter
        implements ClaimConverter<ContractAdjustmentParams, ContractAdjustmentParamsModel> {
    @Override
    public ContractAdjustmentParamsModel convert(ContractAdjustmentParams contractAdjustmentParams) {
        ContractAdjustmentParamsModel contractAdjustmentParamsModel = new ContractAdjustmentParamsModel();
        contractAdjustmentParamsModel.setContractTemplateId(contractAdjustmentParams.getTemplate().getId());
        return contractAdjustmentParamsModel;
    }
}
