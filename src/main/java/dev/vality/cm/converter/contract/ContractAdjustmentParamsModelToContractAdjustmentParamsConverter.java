package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ContractAdjustmentParamsModel;
import dev.vality.damsel.claim_management.ContractAdjustmentParams;
import dev.vality.damsel.domain.ContractTemplateRef;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentParamsModelToContractAdjustmentParamsConverter
        implements ClaimConverter<ContractAdjustmentParamsModel, ContractAdjustmentParams> {
    @Override
    public ContractAdjustmentParams convert(ContractAdjustmentParamsModel contractAdjustmentParamsModel) {
        return new ContractAdjustmentParams()
                .setTemplate(new ContractTemplateRef(contractAdjustmentParamsModel.getContractTemplateId()));
    }
}
