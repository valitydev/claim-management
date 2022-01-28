package dev.vality.cm.converter.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.shop.CashRegisterParamsModel;
import dev.vality.damsel.claim_management.CashRegisterParams;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CashRegisterParamsModelToCashRegisterParamsConverter
        implements ClaimConverter<CashRegisterParamsModel, CashRegisterParams> {

    @Override
    public CashRegisterParams convert(CashRegisterParamsModel cashRegisterParamsModel) {
        try {
            Map<String, String> providerParamsMap =
                    new ObjectMapper().readValue(cashRegisterParamsModel.getProviderParams(),
                            new TypeReference<Map<String, String>>() {
                            });

            return new CashRegisterParams()
                    .setCashRegisterProviderId(cashRegisterParamsModel.getProviderId())
                    .setCashRegisterProviderParams(providerParamsMap);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to convert cashRegisterParamsModel", e);
        }
    }
}
