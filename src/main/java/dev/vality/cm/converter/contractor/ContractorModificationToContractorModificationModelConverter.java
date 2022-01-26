package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorCreationModificationModel;
import dev.vality.cm.model.contractor.ContractorIdentificationLevelModificationModel;
import dev.vality.cm.model.contractor.ContractorModificationModel;
import dev.vality.damsel.claim_management.ContractorModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorModificationToContractorModificationModelConverter
        implements ClaimConverter<ContractorModification, ContractorModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorModificationModel convert(ContractorModification contractorModification) {
        switch (contractorModification.getSetField()) {
            case CREATION:
                return conversionService
                        .convert(contractorModification.getCreation(), ContractorCreationModificationModel.class);
            case IDENTIFICATION_LEVEL_MODIFICATION:
                return conversionService.convert(contractorModification.getIdentificationLevelModification(),
                        ContractorIdentificationLevelModificationModel.class);
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", contractorModification.getSetField()));
        }
    }
}
