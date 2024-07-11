package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractPayoutToolModificationModel;
import dev.vality.damsel.claim_management.PayoutToolModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolModificationUnitToContractPayoutToolModificationModelConverter
        implements ClaimConverter<PayoutToolModificationUnit, ContractPayoutToolModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractPayoutToolModificationModel convert(PayoutToolModificationUnit payoutToolModificationUnit) {
        ContractPayoutToolModificationModel payoutToolModificationModel = conversionService
                .convert(payoutToolModificationUnit.getModification(), ContractPayoutToolModificationModel.class);
        if (payoutToolModificationModel == null) {
            throw new IllegalStateException("ContractPayoutToolModificationModel can't be null");
        }
        payoutToolModificationModel.setPayoutToolId(payoutToolModificationUnit.getPayoutToolId());
        return payoutToolModificationModel;
    }
}
