package com.rbkmoney.cm.handler;

import com.rbkmoney.cm.util.ClaimEventFactory;
import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.msgpack.Value;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
public class ClaimHandlerEventSinkDecorator implements ClaimManagementSrv.Iface {

    private final ClaimManagementSrv.Iface iface;

    private final KafkaTemplate<String, TBase> kafkaTemplate;

    private final ClaimEventFactory claimEventFactory;

    private final RetryTemplate retryTemplate;

    @org.springframework.beans.factory.annotation.Value("kafka.topic.claim.event.sink")
    private String eventSinkTopic;

    @Override
    @Transactional
    public Claim createClaim(String partyId, List<Modification> changeset) throws TException {
        Claim claim = iface.createClaim(partyId, changeset);

        retryTemplate.execute(arg0 -> {
            kafkaTemplate.send(eventSinkTopic, new Event()
                    .setChange(claimEventFactory.createCreatedClaimEvent(partyId, changeset, claim))
            );
            return null;
        });

        return claim;
    }

    @Override
    @Transactional
    public void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset) throws PartyNotFound, ChangesetConflict, InvalidChangeset, InvalidRequest, TException {
        iface.updateClaim(partyId, claimId, revision, changeset);

        retryTemplate.execute(arg0 -> {
            kafkaTemplate.send(eventSinkTopic, new Event()
                    .setChange(claimEventFactory.createUpdateClaimEvent(partyId, claimId, revision, changeset))
            );
            return null;
        });
    }

    @Override
    @Transactional
    public void acceptClaim(String partyId, long claimId, int revision) throws PartyNotFound, ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, ChangesetConflict, InvalidChangeset, TException {
        iface.acceptClaim(partyId, claimId, revision);

        retryTemplate.execute(arg0 -> {
            kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.accepted(new ClaimAccepted())));
            return null;
        });
    }

    @Override
    @Transactional
    public void denyClaim(String partyId, long claimId, int revision, String reason) throws TException {
        iface.denyClaim(partyId, claimId, revision, reason);

        retryTemplate.execute(arg0 -> {
            kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.denied(new ClaimDenied())));
            return null;
        });
    }

    @Override
    @Transactional
    public void revokeClaim(String partyId, long claimId, int revision, String reason) throws TException {
        iface.revokeClaim(partyId, claimId, revision, reason);

        retryTemplate.execute(arg0 -> {
            kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.revoked(new ClaimRevoked())));
            return null;
        });
    }

    @Override
    @Transactional
    public void requestClaimReview(String partyId, long claimId, int revision) throws TException {
        iface.requestClaimReview(partyId, claimId, revision);

        retryTemplate.execute(arg0 -> {
            kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.review(new ClaimReview())));
            return null;
        });
    }

    @Override
    @Transactional
    public void requestClaimChanges(String partyId, long claimId, int revision) throws TException {
        iface.requestClaimChanges(partyId, claimId, revision);

        retryTemplate.execute(arg0 -> {
            kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.pending(new ClaimPending())));
            return null;
        });
    }

    @Override
    @Transactional
    public Value getMetadata(String partyId, long claimId, String key) throws TException {
        return iface.getMetadata(partyId, claimId, key);
    }

    @Override
    @Transactional
    public void setMetadata(String partyId, long claimId, String key, Value value) throws TException {
        iface.setMetadata(partyId, claimId, key, value);
    }

    @Override
    @Transactional
    public void removeMetadata(String partyId, long claimId, String key) throws TException {
        iface.removeMetadata(partyId, claimId, key);
    }

    @Override
    @Transactional
    public Claim getClaim(String partyId, long claimId) throws TException {
        return iface.getClaim(partyId, claimId);
    }

    @Override
    @Transactional
    public ClaimSearchResponse searchClaims(ClaimSearchQuery claimRequest) throws TException {
        return iface.searchClaims(claimRequest);
    }

}
