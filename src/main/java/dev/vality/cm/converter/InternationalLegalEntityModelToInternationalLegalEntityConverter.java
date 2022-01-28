package dev.vality.cm.converter;

import dev.vality.cm.model.InternationalLegalEntityModel;
import dev.vality.damsel.domain.CountryCode;
import dev.vality.damsel.domain.CountryRef;
import dev.vality.damsel.domain.InternationalLegalEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InternationalLegalEntityModelToInternationalLegalEntityConverter
        implements ClaimConverter<InternationalLegalEntityModel, InternationalLegalEntity> {

    @Override
    public InternationalLegalEntity convert(InternationalLegalEntityModel internationalLegalEntityModel) {
        return new InternationalLegalEntity()
                .setLegalName(internationalLegalEntityModel.getLegalName())
                .setTradingName(internationalLegalEntityModel.getTradingName())
                .setActualAddress(internationalLegalEntityModel.getActualAddress())
                .setRegisteredNumber(internationalLegalEntityModel.getRegisteredNumber())
                .setRegisteredAddress(internationalLegalEntityModel.getRegisteredAddress())
                .setCountry(StringUtils.hasLength(internationalLegalEntityModel.getCountryCode())
                        ? new CountryRef(CountryCode.valueOf(internationalLegalEntityModel.getCountryCode()))
                        : null);
    }

}
