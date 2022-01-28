package dev.vality.cm.converter.document;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.document.DocumentModificationModel;
import dev.vality.cm.model.document.DocumentModificationTypeEnum;
import dev.vality.damsel.claim_management.DocumentModificationUnit;
import dev.vality.geck.common.util.TBaseUtil;
import org.springframework.stereotype.Component;

@Component
public class DocumentModificationUnitToDocumentModificationModelConverter
        implements ClaimConverter<DocumentModificationUnit, DocumentModificationModel> {

    @Override
    public DocumentModificationModel convert(DocumentModificationUnit documentModificationUnit) {
        DocumentModificationModel documentModificationModel = new DocumentModificationModel();
        documentModificationModel.setDocumentId(documentModificationUnit.getId());
        documentModificationModel.setDocumentModificationType(
                TBaseUtil.unionFieldToEnum(
                        documentModificationUnit.getModification(),
                        DocumentModificationTypeEnum.class
                )
        );
        return documentModificationModel;
    }
}
