package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorCreationModificationModel;
import dev.vality.damsel.claim_management.ContractorModification;
import dev.vality.damsel.domain.Contractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorCreationModificationModelToContractorModificationConverter
        implements ClaimConverter<ContractorCreationModificationModel, ContractorModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorModification convert(ContractorCreationModificationModel contractorCreationModificationModel) {
        return ContractorModification.creation(
                conversionService.convert(contractorCreationModificationModel, Contractor.class)
        );
    }
}
