package dev.vality.cm.model.file;

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
