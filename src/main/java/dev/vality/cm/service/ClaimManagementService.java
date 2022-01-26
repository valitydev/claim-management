package dev.vality.cm.service;

import dev.vality.cm.model.ClaimModel;
import dev.vality.cm.model.ClaimStatusEnum;
import dev.vality.cm.model.ClaimStatusModel;
import dev.vality.cm.model.MetadataModel;
import dev.vality.cm.search.ClaimPageSearchParameters;
import dev.vality.cm.search.ClaimPageSearchRequest;
import dev.vality.cm.search.ClaimPageSearchResponse;
import dev.vality.damsel.claim_management.Claim;
import dev.vality.damsel.claim_management.Modification;
import dev.vality.damsel.claim_management.ModificationChange;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClaimManagementService {

    Claim createClaim(String partyId, List<Modification> changeset);

    void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset);

    ClaimModel getClaim(String partyId, long claimId);

    ClaimModel failClaimAcceptance(String partyId, long claimId, int revision);

    ClaimModel pendingAcceptanceClaim(String partyId, long claimId, int revision);

    ClaimModel acceptClaim(String partyId, long claimId, int revision);

    ClaimModel revokeClaim(String partyId, long claimId, int revision, String reason);

    ClaimModel denyClaim(String partyId, long claimId, int revision, String reason);

    ClaimModel requestClaimReview(String partyId, long claimId, int revision);

    ClaimModel requestClaimChanges(String partyId, long claimId, int revision);

    ClaimModel changeStatus(String partyId,
                            long claimId,
                            int revision,
                            ClaimStatusModel targetClaimStatus,
                            List<ClaimStatusEnum> expectedStatuses);

    ClaimPageSearchResponse searchClaims(ClaimPageSearchRequest claimSearchRequest,
                                         String continuationToken,
                                         int limit);

    Page<ClaimModel> searchClaims(ClaimPageSearchRequest claimSearchRequest,
                                  ClaimPageSearchParameters claimSearchParameters);

    MetadataModel getMetadata(String partyId, long claimId, String key);

    void setMetadata(String partyId, long claimId, String key, MetadataModel metadataModel);

    void removeMetadata(String partyId, long claimId, String key);

    void updateModification(String partyId,
                            long id,
                            int revision,
                            long modificationId,
                            ModificationChange modificationChange);

    void removeModification(String partyId, long id, int revision, long modificationId);
}
