package dev.vality.cm.converter;

import dev.vality.cm.model.RussianPrivateEntityModel;
import dev.vality.damsel.domain.ContactInfo;
import dev.vality.damsel.domain.RussianPrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianPrivateEntityModelToRussianPrivateEntityConverter
        implements ClaimConverter<RussianPrivateEntityModel, RussianPrivateEntity> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RussianPrivateEntity convert(RussianPrivateEntityModel russianPrivateEntityModel) {
        return new RussianPrivateEntity()
                .setSecondName(russianPrivateEntityModel.getSecondName())
                .setFirstName(russianPrivateEntityModel.getFirstName())
                .setMiddleName(russianPrivateEntityModel.getMiddleName())
                .setContactInfo(
                        conversionService.convert(russianPrivateEntityModel.getContactInfo(), ContactInfo.class));
    }
}
