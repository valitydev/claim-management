package dev.vality.cm.converter;

import dev.vality.cm.model.InternationalBankDetailsModel;
import dev.vality.damsel.domain.InternationalBankDetails;
import org.springframework.stereotype.Component;

@Component
public class InternationalBankDetailsToInternationalBankDetailsModelConverter
        implements ClaimConverter<InternationalBankDetails, InternationalBankDetailsModel> {

    @Override
    public InternationalBankDetailsModel convert(InternationalBankDetails internationalBankDetails) {
        InternationalBankDetailsModel internationalBankDetailsModel = new InternationalBankDetailsModel();
        internationalBankDetailsModel.setName(internationalBankDetails.getName());
        internationalBankDetailsModel.setAbaRtn(internationalBankDetails.getAbaRtn());
        internationalBankDetailsModel.setAddress(internationalBankDetails.getAddress());
        internationalBankDetailsModel.setBic(internationalBankDetails.getBic());
        if (internationalBankDetails.isSetCountry()) {
            internationalBankDetailsModel.setCountryCode(internationalBankDetails.getCountry().getValue());
        }
        return internationalBankDetailsModel;
    }
}
