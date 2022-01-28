package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractContractorChangeModificationModel;
import dev.vality.damsel.claim_management.ContractModification;
import org.springframework.stereotype.Component;

@Component
public class ContractContractorChangeModificationModelToContractModificationConverter
        implements ClaimConverter<ContractContractorChangeModificationModel, ContractModification> {

    @Override
    public ContractModification convert(
            ContractContractorChangeModificationModel contractContractorChangeModificationModel) {
        return ContractModification.contractor_modification(
                contractContractorChangeModificationModel.getContractorId()
        );
    }
}
