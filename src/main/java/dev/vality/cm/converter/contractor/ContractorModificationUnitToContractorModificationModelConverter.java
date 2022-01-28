package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorModificationModel;
import dev.vality.damsel.claim_management.ContractorModification;
import dev.vality.damsel.claim_management.ContractorModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorModificationUnitToContractorModificationModelConverter
        implements ClaimConverter<ContractorModificationUnit, ContractorModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorModificationModel convert(ContractorModificationUnit contractorModificationUnit) {
        ContractorModification contractorModification = contractorModificationUnit.getModification();
        ContractorModificationModel contractorModificationModel =
                conversionService.convert(contractorModification, ContractorModificationModel.class);
        if (contractorModificationModel == null) {
            throw new IllegalStateException("ContractorModificationModel can't be null");
        }
        contractorModificationModel.setContractorId(contractorModificationUnit.getId());
        return contractorModificationModel;
    }
}
