package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.ContractParamsModel;
import dev.vality.cm.model.contract.ContractCreationModificationModel;
import dev.vality.damsel.claim_management.ContractParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractParamsToContractCreationModificationModelConverter
        implements ClaimConverter<ContractParams, ContractCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractCreationModificationModel convert(ContractParams contractParams) {
        ContractCreationModificationModel contractCreationModificationModel = new ContractCreationModificationModel();
        contractCreationModificationModel
                .setContractParams(conversionService.convert(contractParams, ContractParamsModel.class));
        return contractCreationModificationModel;
    }
}
