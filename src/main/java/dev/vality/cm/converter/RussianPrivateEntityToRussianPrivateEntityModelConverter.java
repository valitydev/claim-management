package dev.vality.cm.converter;

import dev.vality.cm.model.ContactInfoModel;
import dev.vality.cm.model.RussianPrivateEntityModel;
import dev.vality.damsel.domain.RussianPrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianPrivateEntityToRussianPrivateEntityModelConverter
        implements ClaimConverter<RussianPrivateEntity, RussianPrivateEntityModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RussianPrivateEntityModel convert(RussianPrivateEntity russianPrivateEntity) {
        RussianPrivateEntityModel russianPrivateEntityModel = new RussianPrivateEntityModel();
        russianPrivateEntityModel.setFirstName(russianPrivateEntity.getFirstName());
        russianPrivateEntityModel.setSecondName(russianPrivateEntity.getSecondName());
        russianPrivateEntityModel.setMiddleName(russianPrivateEntity.getMiddleName());
        russianPrivateEntityModel.setContactInfo(
                conversionService.convert(russianPrivateEntity.getContactInfo(), ContactInfoModel.class));

        return russianPrivateEntityModel;
    }
}
