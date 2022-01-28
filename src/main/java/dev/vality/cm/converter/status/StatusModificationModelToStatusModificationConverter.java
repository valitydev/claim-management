package dev.vality.cm.converter.status;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.status.StatusModificationModel;
import dev.vality.damsel.claim_management.StatusChanged;
import dev.vality.damsel.claim_management.StatusModification;
import org.springframework.stereotype.Component;

@Component
public class StatusModificationModelToStatusModificationConverter
        implements ClaimConverter<StatusModificationModel, StatusModification> {

    @Override
    public StatusModification convert(StatusModificationModel statusModificationModel) {
        switch (statusModificationModel.getStatusModificationType()) {
            case change:
                return StatusModification.change(new StatusChanged());
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", statusModificationModel.getStatusModificationType()));
        }
    }

}
