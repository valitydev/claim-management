package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorIdentificationLevelModificationModel;
import dev.vality.damsel.claim_management.ContractorModification;
import dev.vality.damsel.domain.ContractorIdentificationLevel;
import org.springframework.stereotype.Component;

@Component
public class ContractorIdentificationLevelModificationModelToContractorModificationConverter
        implements ClaimConverter<ContractorIdentificationLevelModificationModel, ContractorModification> {

    @Override
    public ContractorModification convert(
            ContractorIdentificationLevelModificationModel contractorIdentificationLevelModificationModel) {
        return ContractorModification.identification_level_modification(
                ContractorIdentificationLevel.findByValue(
                        contractorIdentificationLevelModificationModel
                                .getContractorIdentificationLevel()
                                .getLevel()
                )
        );
    }
}
