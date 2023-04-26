package dev.vality.cm.converter.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.MetadataModel;
import dev.vality.cm.model.identity.IdentityCreationModificationModel;
import dev.vality.cm.model.identity.IdentityParamsModel;
import dev.vality.damsel.claim_management.IdentityParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class IdentityParamsToIdentityCreationModificationModelConverter
        implements ClaimConverter<IdentityParams, IdentityCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public IdentityCreationModificationModel convert(IdentityParams identityParams) {
        IdentityParamsModel identityParamsModel = new IdentityParamsModel();
        identityParamsModel.setPartyId(identityParams.getPartyId());
        identityParamsModel.setName(identityParams.getName());
        identityParamsModel.setProvider(identityParams.getProvider());
        if (identityParams.isSetMetadata()) {
            try {
                identityParamsModel.setMetadata(
                        objectMapper.writeValueAsString(identityParams.getMetadata().entrySet().stream()
                                .map(entry -> conversionService.convert(entry, MetadataModel.class))
                                .collect(Collectors.toList()))
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Couldn't convert identity metadata to json string: " + e);
            }
        }
        IdentityCreationModificationModel identityCreationModificationModel = new IdentityCreationModificationModel();
        identityCreationModificationModel.setIdentityParams(identityParamsModel);
        return identityCreationModificationModel;
    }
}
