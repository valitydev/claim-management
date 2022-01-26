package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorModificationModel;
import dev.vality.damsel.claim_management.ContractorModificationUnit;
import dev.vality.damsel.claim_management.PartyModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorModificationModelToPartyModificationConverter
        implements ClaimConverter<ContractorModificationModel, PartyModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModification convert(ContractorModificationModel contractorModificationModel) {
        return PartyModification.contractor_modification(
                conversionService.convert(contractorModificationModel, ContractorModificationUnit.class)
        );
    }
}
