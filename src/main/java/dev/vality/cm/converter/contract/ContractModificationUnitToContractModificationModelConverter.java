package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractModificationModel;
import dev.vality.damsel.claim_management.ContractModification;
import dev.vality.damsel.claim_management.ContractModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractModificationUnitToContractModificationModelConverter
        implements ClaimConverter<ContractModificationUnit, ContractModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModificationModel convert(ContractModificationUnit contractModificationUnit) {
        ContractModification contractModification = contractModificationUnit.getModification();
        ContractModificationModel contractModificationModel =
                conversionService.convert(contractModification, ContractModificationModel.class);
        if (contractModificationModel == null) {
            throw new IllegalStateException("ContractModificationModel can't be null");
        }
        contractModificationModel.setContractId(contractModificationUnit.getId());
        return contractModificationModel;
    }
}
