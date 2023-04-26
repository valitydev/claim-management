package dev.vality.cm.converter.newwallet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.MetadataModel;
import dev.vality.cm.model.newwallet.NewWalletCreationModificationModel;
import dev.vality.cm.model.newwallet.NewWalletParamsModel;
import dev.vality.damsel.claim_management.NewWalletParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class NewWalletParamsToNewWalletCreationModificationModelConverter
        implements ClaimConverter<NewWalletParams, NewWalletCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NewWalletCreationModificationModel convert(NewWalletParams newWalletParams) {
        NewWalletParamsModel newWalletParamsModel = new NewWalletParamsModel();
        newWalletParamsModel.setIdentityId(newWalletParams.getIdentityId());
        newWalletParamsModel.setName(newWalletParams.getName());
        newWalletParamsModel.setCurrency(newWalletParams.getCurrency().getSymbolicCode());
        if (newWalletParams.isSetMetadata()) {
            try {
                newWalletParamsModel.setMetadata(
                        objectMapper.writeValueAsString(newWalletParams.getMetadata().entrySet().stream()
                                .map(entry -> conversionService.convert(entry, MetadataModel.class))
                                .collect(Collectors.toList()))
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Couldn't convert newWallet metadata to json string: " + e);
            }
        }
        NewWalletCreationModificationModel newWalletCreationModificationModel =
                new NewWalletCreationModificationModel();
        newWalletCreationModificationModel.setNewWalletParams(newWalletParamsModel);
        return newWalletCreationModificationModel;
    }
}
