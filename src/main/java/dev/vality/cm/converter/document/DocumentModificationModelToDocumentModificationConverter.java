package dev.vality.cm.converter.document;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.document.DocumentModificationModel;
import dev.vality.damsel.claim_management.DocumentChanged;
import dev.vality.damsel.claim_management.DocumentCreated;
import dev.vality.damsel.claim_management.DocumentModification;
import org.springframework.stereotype.Component;

@Component
public class DocumentModificationModelToDocumentModificationConverter
        implements ClaimConverter<DocumentModificationModel, DocumentModification> {

    @Override
    public DocumentModification convert(DocumentModificationModel documentModificationModel) {
        switch (documentModificationModel.getDocumentModificationType()) {
            case creation:
                return DocumentModification.creation(new DocumentCreated());
            case changed:
                return DocumentModification.changed(new DocumentChanged());
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", documentModificationModel.getDocumentModificationType()));
        }
    }

}
