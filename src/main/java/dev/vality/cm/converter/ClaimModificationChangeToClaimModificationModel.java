package dev.vality.cm.converter;

import dev.vality.cm.model.ClaimModificationModel;
import dev.vality.cm.model.comment.CommentModificationModel;
import dev.vality.cm.model.document.DocumentModificationModel;
import dev.vality.cm.model.file.FileModificationModel;
import dev.vality.damsel.claim_management.ClaimModificationChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ClaimModificationChangeToClaimModificationModel
        implements ClaimConverter<ClaimModificationChange, ClaimModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModificationModel convert(ClaimModificationChange claimModificationChange) {
        switch (claimModificationChange.getSetField()) {
            case FILE_MODIFICATION:
                return conversionService
                        .convert(claimModificationChange.getFileModification(), FileModificationModel.class);
            case COMMENT_MODIFICATION:
                return conversionService
                        .convert(claimModificationChange.getCommentModification(), CommentModificationModel.class);
            case DOCUMENT_MODIFICATION:
                return conversionService
                        .convert(claimModificationChange.getDocumentModification(), DocumentModificationModel.class);
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", claimModificationChange.getSetField()));
        }
    }
}
