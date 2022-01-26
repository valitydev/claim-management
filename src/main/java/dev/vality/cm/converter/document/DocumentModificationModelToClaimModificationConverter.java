package dev.vality.cm.converter.document;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.document.DocumentModificationModel;
import dev.vality.damsel.claim_management.ClaimModification;
import dev.vality.damsel.claim_management.DocumentModification;
import dev.vality.damsel.claim_management.DocumentModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class DocumentModificationModelToClaimModificationConverter
        implements ClaimConverter<DocumentModificationModel, ClaimModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModification convert(DocumentModificationModel documentModificationModel) {
        return ClaimModification.document_modification(
                new DocumentModificationUnit()
                        .setId(documentModificationModel.getDocumentId())
                        .setModification(
                                conversionService.convert(documentModificationModel, DocumentModification.class)
                        )
        );
    }
}
