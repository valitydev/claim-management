package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractAdjustmentModificationModel;
import dev.vality.damsel.claim_management.ContractAdjustmentModification;
import dev.vality.damsel.claim_management.ContractAdjustmentModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractAdjustmentModificationModelToContractAdjustmentModificationUnitConverter
        implements ClaimConverter<ContractAdjustmentModificationModel, ContractAdjustmentModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractAdjustmentModificationUnit convert(
            ContractAdjustmentModificationModel contractAdjustmentModificationModel) {
        return new ContractAdjustmentModificationUnit()
                .setAdjustmentId(contractAdjustmentModificationModel.getContractAdjustmentId())
                .setModification(
                        conversionService
                                .convert(contractAdjustmentModificationModel, ContractAdjustmentModification.class)
                );
    }
}
