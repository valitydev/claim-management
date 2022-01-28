package dev.vality.cm.converter.file;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.file.FileModificationModel;
import dev.vality.damsel.claim_management.ClaimModification;
import dev.vality.damsel.claim_management.FileModification;
import dev.vality.damsel.claim_management.FileModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class FileModificationModelToClaimModificationConverter
        implements ClaimConverter<FileModificationModel, ClaimModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModification convert(FileModificationModel fileModificationModel) {
        return ClaimModification.file_modification(
                new FileModificationUnit()
                        .setId(fileModificationModel.getFileId())
                        .setModification(
                                conversionService.convert(fileModificationModel, FileModification.class)
                        )
        );
    }
}
