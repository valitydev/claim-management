package dev.vality.cm.converter;

import dev.vality.cm.model.PayoutToolParamsModel;
import dev.vality.damsel.claim_management.PayoutToolParams;
import dev.vality.damsel.domain.CurrencyRef;
import dev.vality.damsel.domain.PayoutToolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PayoutToolParamsModelToPayoutToolParamsConverter
        implements ClaimConverter<PayoutToolParamsModel, PayoutToolParams> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PayoutToolParams convert(PayoutToolParamsModel payoutToolParamsModel) {
        return new PayoutToolParams()
                .setCurrency(new CurrencyRef(payoutToolParamsModel.getCurrencySymbolicCode()))
                .setToolInfo(
                        conversionService.convert(payoutToolParamsModel.getPayoutToolInfo(), PayoutToolInfo.class));
    }
}
