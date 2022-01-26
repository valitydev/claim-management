package dev.vality.cm.converter.comment;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.comment.CommentModificationModel;
import dev.vality.damsel.claim_management.CommentChanged;
import dev.vality.damsel.claim_management.CommentCreated;
import dev.vality.damsel.claim_management.CommentDeleted;
import dev.vality.damsel.claim_management.CommentModification;
import org.springframework.stereotype.Component;

@Component
public class CommentModificationModelToCommentModificationConverter
        implements ClaimConverter<CommentModificationModel, CommentModification> {

    @Override
    public CommentModification convert(CommentModificationModel commentModificationModel) {
        switch (commentModificationModel.getCommentModificationType()) {
            case creation:
                return CommentModification.creation(new CommentCreated());
            case deletion:
                return CommentModification.deletion(new CommentDeleted());
            case changed:
                return CommentModification.changed(new CommentChanged());
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", commentModificationModel.getCommentModificationType()));
        }
    }

}
