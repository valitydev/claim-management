package dev.vality.cm.converter;

import dev.vality.cm.model.ClaimStatusEnum;
import dev.vality.cm.model.ClaimStatusModel;
import dev.vality.damsel.claim_management.ClaimStatus;
import dev.vality.geck.common.util.TBaseUtil;
import org.springframework.stereotype.Component;

@Component
public class ClaimStatusToClaimStatusModelConverter implements ClaimConverter<ClaimStatus, ClaimStatusModel> {
    @Override
    public ClaimStatusModel convert(ClaimStatus claimStatus) {
        ClaimStatusModel claimStatusModel = new ClaimStatusModel();
        claimStatusModel.setClaimStatusEnum(TBaseUtil.unionFieldToEnum(claimStatus, ClaimStatusEnum.class));
        claimStatusModel.setClaimStatusReason(extractReason(claimStatus));
        return claimStatusModel;
    }

    private String extractReason(ClaimStatus claimStatus) {
        switch (claimStatus.getSetField()) {
            case DENIED:
                return claimStatus.getDenied().getReason();
            case REVOKED:
                return claimStatus.getRevoked().getReason();
            default:
                return null;
        }
    }
}
