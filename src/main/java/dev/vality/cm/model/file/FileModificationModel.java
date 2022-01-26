package dev.vality.cm.model.file;

import dev.vality.cm.model.ClaimModificationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FileModificationModel extends ClaimModificationModel {

    @NotNull
    @Column(nullable = false)
    private String fileId;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileModificationTypeEnum fileModificationType;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof FileModificationModel
                && fileId.equals(((FileModificationModel) that).getFileId())
                && fileModificationType == ((FileModificationModel) that).getFileModificationType();
    }

}
