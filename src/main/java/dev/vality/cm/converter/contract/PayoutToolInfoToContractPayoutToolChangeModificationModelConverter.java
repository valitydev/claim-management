package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.PayoutToolInfoModel;
import dev.vality.cm.model.contract.ContractPayoutToolChangeModificationModel;
import dev.vality.damsel.domain.PayoutToolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolInfoToContractPayoutToolChangeModificationModelConverter
        implements ClaimConverter<PayoutToolInfo, ContractPayoutToolChangeModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractPayoutToolChangeModificationModel convert(PayoutToolInfo payoutToolInfo) {
        ContractPayoutToolChangeModificationModel payoutToolInfoChangeModificationModel =
                new ContractPayoutToolChangeModificationModel();
        payoutToolInfoChangeModificationModel
                .setPayoutToolInfoModel(conversionService.convert(payoutToolInfo, PayoutToolInfoModel.class));
        return payoutToolInfoChangeModificationModel;
    }
}
