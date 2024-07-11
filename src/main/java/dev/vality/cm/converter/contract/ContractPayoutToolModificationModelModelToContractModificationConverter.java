package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractPayoutToolModificationModel;
import dev.vality.damsel.claim_management.ContractModification;
import dev.vality.damsel.claim_management.PayoutToolModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractPayoutToolModificationModelModelToContractModificationConverter
        implements ClaimConverter<ContractPayoutToolModificationModel, ContractModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModification convert(ContractPayoutToolModificationModel contractPayoutToolModificationModel) {
        return ContractModification.payout_tool_modification(
                conversionService.convert(contractPayoutToolModificationModel, PayoutToolModificationUnit.class)
        );
    }
}
