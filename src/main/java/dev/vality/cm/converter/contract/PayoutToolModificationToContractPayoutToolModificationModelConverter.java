package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractPayoutToolChangeModificationModel;
import dev.vality.cm.model.contract.ContractPayoutToolCreationModificationModel;
import dev.vality.cm.model.contract.ContractPayoutToolModificationModel;
import dev.vality.damsel.claim_management.PayoutToolModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolModificationToContractPayoutToolModificationModelConverter
        implements ClaimConverter<PayoutToolModification, ContractPayoutToolModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractPayoutToolModificationModel convert(PayoutToolModification payoutToolModification) {
        switch (payoutToolModification.getSetField()) {
            case CREATION:
                return conversionService.convert(payoutToolModification.getCreation(),
                        ContractPayoutToolCreationModificationModel.class);
            case INFO_MODIFICATION:
                return conversionService.convert(payoutToolModification.getInfoModification(),
                        ContractPayoutToolChangeModificationModel.class);
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", payoutToolModification.getSetField()));
        }
    }
}
