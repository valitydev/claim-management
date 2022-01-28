package dev.vality.cm.converter;

import dev.vality.cm.model.InternationalBankDetailsModel;
import dev.vality.damsel.domain.CountryCode;
import dev.vality.damsel.domain.InternationalBankDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InternationalBankDetailsModelToInternationalBankDetailsConverter
        implements ClaimConverter<InternationalBankDetailsModel, InternationalBankDetails> {

    @Override
    public InternationalBankDetails convert(InternationalBankDetailsModel internationalBankDetailsModel) {
        return new InternationalBankDetails()
                .setBic(internationalBankDetailsModel.getBic())
                .setAbaRtn(internationalBankDetailsModel.getAbaRtn())
                .setAddress(internationalBankDetailsModel.getAddress())
                .setName(internationalBankDetailsModel.getName())
                .setCountry(
                        Optional.ofNullable(internationalBankDetailsModel.getCountryCode())
                                .map(CountryCode::findByValue)
                                .orElse(null)
                );
    }
}
