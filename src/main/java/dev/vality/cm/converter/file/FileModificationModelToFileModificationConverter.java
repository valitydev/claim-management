package dev.vality.cm.converter.file;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.file.FileModificationModel;
import dev.vality.damsel.claim_management.FileChanged;
import dev.vality.damsel.claim_management.FileCreated;
import dev.vality.damsel.claim_management.FileDeleted;
import dev.vality.damsel.claim_management.FileModification;
import org.springframework.stereotype.Component;

@Component
public class FileModificationModelToFileModificationConverter
        implements ClaimConverter<FileModificationModel, FileModification> {

    @Override
    public FileModification convert(FileModificationModel fileModificationModel) {
        switch (fileModificationModel.getFileModificationType()) {
            case creation:
                return FileModification.creation(new FileCreated());
            case deletion:
                return FileModification.deletion(new FileDeleted());
            case changed:
                return FileModification.changed(new FileChanged());
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", fileModificationModel.getFileModificationType()));
        }
    }

}
