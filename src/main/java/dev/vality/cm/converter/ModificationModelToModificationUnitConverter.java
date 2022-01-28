package dev.vality.cm.converter;

import dev.vality.cm.model.ModificationModel;
import dev.vality.damsel.claim_management.Modification;
import dev.vality.damsel.claim_management.ModificationUnit;
import dev.vality.damsel.claim_management.UserInfo;
import dev.vality.geck.common.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ModificationModelToModificationUnitConverter
        implements ClaimConverter<ModificationModel, ModificationUnit> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ModificationUnit convert(ModificationModel modificationModel) {
        UserInfo userInfo = conversionService.convert(modificationModel.getUserInfo(), UserInfo.class);
        Modification modification = conversionService.convert(modificationModel, Modification.class);
        return new ModificationUnit()
                .setModificationId(modificationModel.getId())
                .setCreatedAt(TypeUtil.temporalToString(modificationModel.getCreatedAt()))
                .setChangedAt(modificationModel.getChangedAt() != null
                        ? TypeUtil.temporalToString(modificationModel.getChangedAt()) : null)
                .setRemovedAt(modificationModel.getRemovedAt() != null
                        ? TypeUtil.temporalToString(modificationModel.getRemovedAt()) : null)
                .setUserInfo(userInfo)
                .setModification(modification);
    }
}
