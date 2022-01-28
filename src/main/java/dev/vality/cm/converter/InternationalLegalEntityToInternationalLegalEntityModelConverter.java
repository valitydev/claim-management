package dev.vality.cm.converter;

import dev.vality.cm.model.InternationalLegalEntityModel;
import dev.vality.damsel.domain.InternationalLegalEntity;
import org.springframework.stereotype.Component;

@Component
public class InternationalLegalEntityToInternationalLegalEntityModelConverter
        implements ClaimConverter<InternationalLegalEntity, InternationalLegalEntityModel> {

    @Override
    public InternationalLegalEntityModel convert(InternationalLegalEntity internationalLegalEntity) {
        InternationalLegalEntityModel internationalLegalEntityModel = new InternationalLegalEntityModel();
        internationalLegalEntityModel.setActualAddress(internationalLegalEntity.getActualAddress());
        internationalLegalEntityModel.setRegisteredAddress(internationalLegalEntity.getRegisteredAddress());
        internationalLegalEntityModel.setRegisteredNumber(internationalLegalEntity.getRegisteredNumber());
        internationalLegalEntityModel.setLegalName(internationalLegalEntity.getLegalName());
        internationalLegalEntityModel.setTradingName(internationalLegalEntity.getTradingName());
        if (internationalLegalEntity.isSetCountry()) {
            internationalLegalEntityModel.setCountryCode(internationalLegalEntity.getCountry().getId().name());
        }
        return internationalLegalEntityModel;
    }

}
