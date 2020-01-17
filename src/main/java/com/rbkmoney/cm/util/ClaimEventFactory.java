package com.rbkmoney.cm.util;

import com.rbkmoney.damsel.claim_management.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimEventFactory {

    public Event createCreatedClaimEvent(String partyId, List<Modification> changeset, Claim claim) {
        Change change = new Change();
        change.setCreated(new ClaimCreated()
                .setCreatedAt(claim.getCreatedAt())
                .setChangeset(changeset)
                .setId(claim.getId())
                .setPartyId(partyId)
                .setRevision(claim.getRevision())
        );
        return new Event().setChange(change);
    }

    public Event createUpdateClaimEvent(String partyId, long claimId, int revision, List<Modification> changeset) {
        Change change = new Change();
        change.setUpdated(new ClaimUpdated()
                .setId(claimId)
                .setChangeset(changeset)
                .setPartyId(partyId)
                .setRevision(revision)
        );
        return new Event().setChange(change);
    }

    public Event createChangeStatusEvent(String partyId, long claimId, int revision, ClaimStatus claimStatus) {
        Change change = new Change();
        change.setStatusChanged(new ClaimStatusChanged()
                .setId(claimId)
                .setPartyId(partyId)
                .setRevision(revision)
                .setStatus(claimStatus)
        );
        return new Event().setChange(change);
    }

}
