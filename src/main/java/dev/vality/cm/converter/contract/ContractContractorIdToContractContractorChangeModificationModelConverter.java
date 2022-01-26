package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractContractorChangeModificationModel;
import org.springframework.stereotype.Component;

@Component
public class ContractContractorIdToContractContractorChangeModificationModelConverter
        implements ClaimConverter<String, ContractContractorChangeModificationModel> {

    @Override
    public ContractContractorChangeModificationModel convert(String contractorId) {
        ContractContractorChangeModificationModel contractContractorChangeModificationModel =
                new ContractContractorChangeModificationModel();
        contractContractorChangeModificationModel.setContractorId(contractorId);
        return contractContractorChangeModificationModel;
    }

}
