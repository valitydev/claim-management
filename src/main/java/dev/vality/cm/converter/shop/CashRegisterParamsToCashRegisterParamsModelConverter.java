package dev.vality.cm.converter.shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.CashRegisterParamsModel;
import dev.vality.damsel.claim_management.CashRegisterParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CashRegisterParamsToCashRegisterParamsModelConverter
        implements ClaimConverter<CashRegisterParams, CashRegisterParamsModel> {

    @Override
    public CashRegisterParamsModel convert(CashRegisterParams cashRegisterParams) {
        try {
            byte[] providerParams =
                    new ObjectMapper().writeValueAsBytes(cashRegisterParams.getCashRegisterProviderParams());

            CashRegisterParamsModel cashRegisterParamsModel = new CashRegisterParamsModel();
            cashRegisterParamsModel.setProviderId(cashRegisterParams.getCashRegisterProviderId());
            cashRegisterParamsModel.setProviderParams(providerParams);

            return cashRegisterParamsModel;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert cashRegisterParams");
        }
    }

}
