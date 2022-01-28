package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ContractParamsModel;
import dev.vality.damsel.claim_management.ContractParams;
import org.springframework.stereotype.Component;

@Component
public class ContractParamsToContractParamsModelConverter
        implements ClaimConverter<ContractParams, ContractParamsModel> {
    @Override
    public ContractParamsModel convert(ContractParams contractParams) {
        ContractParamsModel contractParamsModel = new ContractParamsModel();
        contractParamsModel.setContractorId(contractParams.getContractorId());
        if (contractParams.isSetTemplate()) {
            contractParamsModel.setContractTemplateId(contractParams.getTemplate().getId());
        }
        if (contractParams.isSetPaymentInstitution()) {
            contractParamsModel.setPaymentInstitutionId(contractParams.getPaymentInstitution().getId());
        }
        return contractParamsModel;
    }
}
