package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractTerminationModificationModel;
import dev.vality.damsel.claim_management.ContractModification;
import dev.vality.damsel.claim_management.ContractTermination;
import org.springframework.stereotype.Component;

@Component
public class ContractTerminationModificationModelToContractModificationConverter
        implements ClaimConverter<ContractTerminationModificationModel, ContractModification> {

    @Override
    public ContractModification convert(ContractTerminationModificationModel contractTerminationModificationModel) {
        return ContractModification.termination(
                new ContractTermination()
                        .setReason(contractTerminationModificationModel.getReason())
        );
    }

}
