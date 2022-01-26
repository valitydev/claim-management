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
public class ContractorModificationModelToContractorModificationUnit
        implements ClaimConverter<ContractorModificationModel, ContractorModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorModificationUnit convert(ContractorModificationModel contractorModificationModel) {
        return new ContractorModificationUnit()
                .setId(contractorModificationModel.getContractorId())
                .setModification(
                        conversionService.convert(contractorModificationModel, ContractorModification.class)
                );
    }
}
