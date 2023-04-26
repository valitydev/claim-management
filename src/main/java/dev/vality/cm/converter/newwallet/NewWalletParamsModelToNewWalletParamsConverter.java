package dev.vality.cm.converter.newwallet;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.MetadataModel;
import dev.vality.cm.model.newwallet.NewWalletParamsModel;
import dev.vality.damsel.claim_management.NewWalletParams;
import dev.vality.damsel.domain.CurrencyRef;
import dev.vality.damsel.msgpack.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NewWalletParamsModelToNewWalletParamsConverter
        implements ClaimConverter<NewWalletParamsModel, NewWalletParams> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public NewWalletParams convert(NewWalletParamsModel newWalletParamsModel) {
        List<MetadataModel> list = conversionService.convert(newWalletParamsModel.getMetadata(), List.class);
        return new NewWalletParams()
                .setIdentityId(newWalletParamsModel.getIdentityId())
                .setName(newWalletParamsModel.getName())
                .setCurrency(new CurrencyRef(newWalletParamsModel.getCurrency()))
                .setMetadata(
                        Optional.ofNullable(list)
                                .map(
                                        metadata -> metadata.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                MetadataModel::getKey,
                                                                metadataModel -> conversionService
                                                                        .convert(metadataModel, Value.class)
                                                        )
                                                )
                                )
                                .orElse(null));
    }
}
