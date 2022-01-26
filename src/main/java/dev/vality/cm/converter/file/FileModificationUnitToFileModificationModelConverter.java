package dev.vality.cm.converter.file;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.file.FileModificationModel;
import dev.vality.cm.model.file.FileModificationTypeEnum;
import dev.vality.damsel.claim_management.FileModificationUnit;
import dev.vality.geck.common.util.TBaseUtil;
import org.springframework.stereotype.Component;

@Component
public class FileModificationUnitToFileModificationModelConverter
        implements ClaimConverter<FileModificationUnit, FileModificationModel> {

    @Override
    public FileModificationModel convert(FileModificationUnit fileModificationUnit) {
        FileModificationModel fileModificationModel = new FileModificationModel();
        fileModificationModel.setFileId(fileModificationUnit.getId());
        fileModificationModel.setFileModificationType(
                TBaseUtil.unionFieldToEnum(
                        fileModificationUnit.getModification(),
                        FileModificationTypeEnum.class
                )
        );
        return fileModificationModel;
    }
}
