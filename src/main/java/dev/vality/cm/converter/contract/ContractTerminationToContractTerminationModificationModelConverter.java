package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractTerminationModificationModel;
import dev.vality.damsel.claim_management.ContractTermination;
import org.springframework.stereotype.Component;

@Component
public class ContractTerminationToContractTerminationModificationModelConverter
        implements ClaimConverter<ContractTermination, ContractTerminationModificationModel> {

    @Override
    public ContractTerminationModificationModel convert(ContractTermination contractTermination) {
        ContractTerminationModificationModel contractTerminationModificationModel =
                new ContractTerminationModificationModel();
        contractTerminationModificationModel.setReason(contractTermination.getReason());
        return contractTerminationModificationModel;
    }

}
