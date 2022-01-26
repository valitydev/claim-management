package dev.vality.cm.converter;

import dev.vality.cm.model.RussianPrivateEntityModel;
import dev.vality.damsel.domain.PrivateEntity;
import dev.vality.damsel.domain.RussianPrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianPrivateEntityModelToPrivateEntityConverter
        implements ClaimConverter<RussianPrivateEntityModel, PrivateEntity> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PrivateEntity convert(RussianPrivateEntityModel russianPrivateEntityModel) {
        return PrivateEntity.russian_private_entity(
                conversionService.convert(russianPrivateEntityModel, RussianPrivateEntity.class)
        );
    }
}
