package dev.vality.cm.converter;

import dev.vality.cm.model.PowerOfAttorneyRepresentativeDocumentModel;
import dev.vality.damsel.domain.LegalAgreement;
import dev.vality.damsel.domain.RepresentativeDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PowerOfAttorneyRepresentativeDocumentModelToRepresentativeDocumentConverter
        implements ClaimConverter<PowerOfAttorneyRepresentativeDocumentModel, RepresentativeDocument> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RepresentativeDocument convert(
            PowerOfAttorneyRepresentativeDocumentModel powerOfAttorneyRepresentativeDocumentModel) {
        return RepresentativeDocument.power_of_attorney(
                conversionService
                        .convert(powerOfAttorneyRepresentativeDocumentModel.getLegalAgreement(), LegalAgreement.class)
        );
    }
}
