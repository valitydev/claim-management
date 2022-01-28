package dev.vality.cm.converter;

import dev.vality.cm.model.ClaimStatusEnum;
import dev.vality.cm.model.ClaimStatusModel;
import dev.vality.damsel.claim_management.*;
import org.springframework.stereotype.Component;

@Component
public class ClaimStatusModelToClaimStatusConverter implements ClaimConverter<ClaimStatusModel, ClaimStatus> {
    @Override
    public ClaimStatus convert(ClaimStatusModel claimStatusModel) {
        ClaimStatusEnum claimStatusEnum = claimStatusModel.getClaimStatusEnum();
        switch (claimStatusEnum) {
            case pending:
                return ClaimStatus.pending(new ClaimPending());
            case review:
                return ClaimStatus.review(new ClaimReview());
            case pending_acceptance:
                return ClaimStatus.pending_acceptance(new ClaimPendingAcceptance());
            case accepted:
                return ClaimStatus.accepted(new ClaimAccepted());
            case denied:
                return ClaimStatus.denied(new ClaimDenied().setReason(claimStatusModel.getClaimStatusReason()));
            case revoked:
                return ClaimStatus.revoked(new ClaimRevoked().setReason(claimStatusModel.getClaimStatusReason()));
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", claimStatusEnum));
        }
    }
}
