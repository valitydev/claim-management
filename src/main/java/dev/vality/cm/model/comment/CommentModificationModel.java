package dev.vality.cm.model.comment;

import dev.vality.cm.model.ClaimModificationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentModificationModel extends ClaimModificationModel {

    @NotNull
    @Column(nullable = false)
    private String commentId;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentModificationTypeEnum commentModificationType;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof CommentModificationModel
                && commentId.equals(((CommentModificationModel) that).getCommentId())
                && commentModificationType == ((CommentModificationModel) that).getCommentModificationType();
    }
}
