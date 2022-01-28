package dev.vality.cm.converter;

import dev.vality.cm.model.PrivateEntityModel;
import dev.vality.cm.model.RussianPrivateEntityModel;
import dev.vality.damsel.domain.PrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PrivateEntityToPrivateEntityModelConverter implements ClaimConverter<PrivateEntity, PrivateEntityModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PrivateEntityModel convert(PrivateEntity privateEntity) {
        switch (privateEntity.getSetField()) {
            case RUSSIAN_PRIVATE_ENTITY:
                return conversionService
                        .convert(privateEntity.getRussianPrivateEntity(), RussianPrivateEntityModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", privateEntity.getSetField()));
        }
    }
}
