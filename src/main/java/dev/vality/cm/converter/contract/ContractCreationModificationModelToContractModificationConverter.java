package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractCreationModificationModel;
import dev.vality.damsel.claim_management.ContractModification;
import dev.vality.damsel.claim_management.ContractParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractCreationModificationModelToContractModificationConverter
        implements ClaimConverter<ContractCreationModificationModel, ContractModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModification convert(ContractCreationModificationModel contractCreationModificationModel) {
        ContractParams contractParams =
                conversionService.convert(contractCreationModificationModel.getContractParams(), ContractParams.class);
        return ContractModification.creation(contractParams);
    }
}
