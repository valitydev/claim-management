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
public class ContractModificationModelToContractModificationUnitConverter
        implements ClaimConverter<ContractModificationModel, ContractModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModificationUnit convert(ContractModificationModel contractModificationModel) {
        return new ContractModificationUnit()
                .setId(contractModificationModel.getContractId())
                .setModification(
                        conversionService.convert(contractModificationModel, ContractModification.class)
                );
    }
}
