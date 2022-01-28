package dev.vality.cm.converter;

import dev.vality.cm.model.ClaimModificationModel;
import dev.vality.cm.model.comment.CommentModificationModel;
import dev.vality.cm.model.document.DocumentModificationModel;
import dev.vality.cm.model.external.info.ExternalInfoModificationModel;
import dev.vality.cm.model.file.FileModificationModel;
import dev.vality.cm.model.status.StatusModificationModel;
import dev.vality.damsel.claim_management.ClaimModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ClaimModificationToClaimModificationModelConverter
        implements ClaimConverter<ClaimModification, ClaimModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModificationModel convert(ClaimModification claimModification) {
        switch (claimModification.getSetField()) {
            case FILE_MODIFICATION:
                return conversionService.convert(claimModification.getFileModification(), FileModificationModel.class);
            case STATUS_MODIFICATION:
                return conversionService
                        .convert(claimModification.getStatusModification(), StatusModificationModel.class);
            case COMMENT_MODIFICATION:
                return conversionService
                        .convert(claimModification.getCommentModification(), CommentModificationModel.class);
            case DOCUMENT_MODIFICATION:
                return conversionService
                        .convert(claimModification.getDocumentModification(), DocumentModificationModel.class);
            case EXTERNAL_INFO_MODIFICATION:
                return conversionService
                        .convert(claimModification.getExternalInfoModification(), ExternalInfoModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", claimModification.getSetField()));
        }
    }
}
