package dev.vality.cm.converter;

import dev.vality.cm.model.ArticlesOfAssociationRepresentativeDocumentModel;
import dev.vality.damsel.domain.ArticlesOfAssociation;
import dev.vality.damsel.domain.RepresentativeDocument;
import org.springframework.stereotype.Component;

@Component
public class ArticlesOfAssociationRepresentativeDocumentModelToRepresentativeDocumentConverter
        implements ClaimConverter<ArticlesOfAssociationRepresentativeDocumentModel, RepresentativeDocument> {
    @Override
    public RepresentativeDocument convert(
            ArticlesOfAssociationRepresentativeDocumentModel articlesOfAssociationRepresentativeDocumentModel) {
        return RepresentativeDocument.articles_of_association(new ArticlesOfAssociation());
    }
}
