package dev.vality.cm.converter;

import dev.vality.cm.model.RussianLegalEntityModel;
import dev.vality.damsel.domain.LegalEntity;
import dev.vality.damsel.domain.RussianLegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianLegalEntityModelToLegalEntityConverter
        implements ClaimConverter<RussianLegalEntityModel, LegalEntity> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public LegalEntity convert(RussianLegalEntityModel russianLegalEntityModel) {
        return LegalEntity.russian_legal_entity(
                conversionService.convert(russianLegalEntityModel, RussianLegalEntity.class)
        );
    }
}
