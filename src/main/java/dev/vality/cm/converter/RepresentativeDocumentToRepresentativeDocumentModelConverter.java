package dev.vality.cm.converter;

import dev.vality.cm.model.ArticlesOfAssociationRepresentativeDocumentModel;
import dev.vality.cm.model.PowerOfAttorneyRepresentativeDocumentModel;
import dev.vality.cm.model.RepresentativeDocumentModel;
import dev.vality.damsel.domain.RepresentativeDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RepresentativeDocumentToRepresentativeDocumentModelConverter
        implements ClaimConverter<RepresentativeDocument, RepresentativeDocumentModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RepresentativeDocumentModel convert(RepresentativeDocument representativeDocument) {
        switch (representativeDocument.getSetField()) {
            case POWER_OF_ATTORNEY:
                return conversionService.convert(representativeDocument.getPowerOfAttorney(),
                        PowerOfAttorneyRepresentativeDocumentModel.class);
            case ARTICLES_OF_ASSOCIATION:
                return new ArticlesOfAssociationRepresentativeDocumentModel();
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", representativeDocument.getSetField()));
        }
    }
}
