package dev.vality.cm.converter;

import dev.vality.cm.model.RepresentativeDocumentModel;
import dev.vality.cm.model.RepresentativeModel;
import dev.vality.damsel.domain.Representative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RepresentativeToRepresentativeModelConverter
        implements ClaimConverter<Representative, RepresentativeModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RepresentativeModel convert(Representative representative) {
        RepresentativeModel representativeModel = new RepresentativeModel();
        representativeModel.setFullName(representative.getFullName());
        representativeModel.setPosition(representative.getPosition());
        representativeModel.setDocument(
                conversionService.convert(representative.getDocument(), RepresentativeDocumentModel.class));
        return representativeModel;
    }
}
