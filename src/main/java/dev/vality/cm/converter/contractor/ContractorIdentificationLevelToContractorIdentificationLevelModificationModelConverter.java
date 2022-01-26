package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorIdentificationLevelModificationModel;
import dev.vality.damsel.domain.ContractorIdentificationLevel;
import dev.vality.geck.common.util.TypeUtil;
import org.springframework.stereotype.Component;

@Component
public class ContractorIdentificationLevelToContractorIdentificationLevelModificationModelConverter
        implements ClaimConverter<ContractorIdentificationLevel, ContractorIdentificationLevelModificationModel> {

    @Override
    public ContractorIdentificationLevelModificationModel convert(
            ContractorIdentificationLevel contractorIdentificationLevel) {
        ContractorIdentificationLevelModificationModel contractorIdentificationLevelModificationModel =
                new ContractorIdentificationLevelModificationModel();
        contractorIdentificationLevelModificationModel.setContractorIdentificationLevel(
                TypeUtil.toEnumField(
                        contractorIdentificationLevel.name(),
                        dev.vality.cm.model.contractor.ContractorIdentificationLevel.class
                )
        );
        return contractorIdentificationLevelModificationModel;
    }

}
