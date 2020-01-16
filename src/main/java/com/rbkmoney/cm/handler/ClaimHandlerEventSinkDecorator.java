package com.rbkmoney.cm.handler;

import com.rbkmoney.cm.util.ClaimEventFactory;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.msgpack.Value;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.springframework.kafka.core.KafkaTemplate;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
public class ClaimHandlerEventSinkDecorator implements ClaimManagementSrv.Iface {

    private final ClaimManagementSrv.Iface iface;

    private final KafkaTemplate<String, TBase> kafkaTemplate;

    private final ClaimEventFactory claimEventFactory;

    @org.springframework.beans.factory.annotation.Value("kafka.topic.claim.event.sink")
    private String eventSinkTopic;

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Claim createClaim(String partyId, List<Modification> changeset) throws TException {
        Claim claim = iface.createClaim(partyId, changeset);

        kafkaTemplate.send(eventSinkTopic, new Event()
                .setChange(claimEventFactory.createCreatedClaimEvent(partyId, changeset, claim))
        );
        return claim;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset) throws TException {
        iface.updateClaim(partyId, claimId, revision, changeset);

        kafkaTemplate.send(eventSinkTopic, new Event()
                .setChange(claimEventFactory.createUpdateClaimEvent(partyId, claimId, revision, changeset))
        );
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void acceptClaim(String partyId, long claimId, int revision) throws TException {
        iface.acceptClaim(partyId, claimId, revision);

        kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.accepted(new ClaimAccepted())));
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void denyClaim(String partyId, long claimId, int revision, String reason) throws TException {
        iface.denyClaim(partyId, claimId, revision, reason);

        kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.denied(new ClaimDenied())));
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void revokeClaim(String partyId, long claimId, int revision, String reason) throws TException {
        iface.revokeClaim(partyId, claimId, revision, reason);

        kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.revoked(new ClaimRevoked())));
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void requestClaimReview(String partyId, long claimId, int revision) throws TException {
        iface.requestClaimReview(partyId, claimId, revision);

        kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.review(new ClaimReview())));
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void requestClaimChanges(String partyId, long claimId, int revision) throws TException {
        iface.requestClaimChanges(partyId, claimId, revision);

        kafkaTemplate.send(eventSinkTopic, claimEventFactory.createChangeStatusEvent(partyId, claimId, revision, ClaimStatus.pending(new ClaimPending())));
    }

    @Override
    public Value getMetadata(String partyId, long claimId, String key) throws TException {
        return iface.getMetadata(partyId, claimId, key);
    }

    @Override
    public void setMetadata(String partyId, long claimId, String key, Value value) throws TException {
        iface.setMetadata(partyId, claimId, key, value);
    }

    @Override
    public void removeMetadata(String partyId, long claimId, String key) throws TException {
        iface.removeMetadata(partyId, claimId, key);
    }

    @Override
    public Claim getClaim(String partyId, long claimId) throws TException {
        return iface.getClaim(partyId, claimId);
    }

    @Override
    public ClaimSearchResponse searchClaims(ClaimSearchQuery claimRequest) throws TException {
        return iface.searchClaims(claimRequest);
    }

}
