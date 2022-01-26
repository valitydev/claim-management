package dev.vality.cm.converter;

import dev.vality.cm.model.InternationalLegalEntityModel;
import dev.vality.damsel.domain.InternationalLegalEntity;
import dev.vality.damsel.domain.LegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class InternationalLegalEntityModelToLegalEntityConverter
        implements ClaimConverter<InternationalLegalEntityModel, LegalEntity> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public LegalEntity convert(InternationalLegalEntityModel internationalLegalEntityModel) {
        return LegalEntity.international_legal_entity(
                conversionService.convert(internationalLegalEntityModel, InternationalLegalEntity.class)
        );
    }
}
