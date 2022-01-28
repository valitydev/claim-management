package dev.vality.cm.converter.comment;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.comment.CommentModificationModel;
import dev.vality.cm.model.comment.CommentModificationTypeEnum;
import dev.vality.damsel.claim_management.CommentModificationUnit;
import dev.vality.geck.common.util.TBaseUtil;
import org.springframework.stereotype.Component;

@Component
public class CommentModificationUnitToCommentModificationModelConverter
        implements ClaimConverter<CommentModificationUnit, CommentModificationModel> {

    @Override
    public CommentModificationModel convert(CommentModificationUnit commentModificationUnit) {
        CommentModificationModel commentModificationModel = new CommentModificationModel();
        commentModificationModel.setCommentId(commentModificationUnit.getId());
        commentModificationModel.setCommentModificationType(
                TBaseUtil.unionFieldToEnum(
                        commentModificationUnit.getModification(),
                        CommentModificationTypeEnum.class
                )
        );
        return commentModificationModel;
    }
}
