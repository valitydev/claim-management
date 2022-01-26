package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractModificationModel;
import dev.vality.damsel.claim_management.ContractModificationUnit;
import dev.vality.damsel.claim_management.PartyModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractModificationModelToPartyModificationConverter
        implements ClaimConverter<ContractModificationModel, PartyModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModification convert(ContractModificationModel contractModificationModel) {
        return PartyModification.contract_modification(
                conversionService.convert(contractModificationModel, ContractModificationUnit.class)
        );
    }
}
