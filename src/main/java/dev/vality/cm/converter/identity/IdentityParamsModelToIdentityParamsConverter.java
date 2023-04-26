package dev.vality.cm.converter.identity;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.MetadataModel;
import dev.vality.cm.model.identity.IdentityParamsModel;
import dev.vality.damsel.claim_management.IdentityParams;
import dev.vality.damsel.msgpack.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IdentityParamsModelToIdentityParamsConverter
        implements ClaimConverter<IdentityParamsModel, IdentityParams> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public IdentityParams convert(IdentityParamsModel identityParamsModel) {
        List<MetadataModel> list = conversionService.convert(identityParamsModel.getMetadata(), List.class);
        return new IdentityParams()
                .setPartyId(identityParamsModel.getPartyId())
                .setName(identityParamsModel.getName())
                .setProvider(identityParamsModel.getProvider())
                .setMetadata(
                        Optional.ofNullable(list)
                                .map(
                                        metadata -> metadata.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                MetadataModel::getKey,
                                                                metadataModel -> conversionService
                                                                        .convert(metadataModel, Value.class))))
                                .orElse(null));
    }
}
