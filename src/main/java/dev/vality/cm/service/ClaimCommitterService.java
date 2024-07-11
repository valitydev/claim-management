package dev.vality.cm.service;

import dev.vality.cm.config.CommitterConfig;
import dev.vality.cm.exception.InvalidChangesetException;
import dev.vality.cm.exception.InvalidClaimStatusException;
import dev.vality.cm.exception.InvalidRevisionException;
import dev.vality.cm.model.ClaimModel;
import dev.vality.cm.model.ClaimStatusEnum;
import dev.vality.cm.model.ModificationModel;
import dev.vality.cm.util.ContextUtil;
import dev.vality.cm.util.JsonUtil;
import dev.vality.damsel.claim_management.Claim;
import dev.vality.damsel.claim_management.ClaimCommitterSrv;
import dev.vality.damsel.claim_management.InvalidChangeset;
import dev.vality.woody.api.flow.WFlow;
import dev.vality.woody.thrift.impl.http.THSpawnClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import jakarta.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ClaimCommitterService {

    private final ClaimManagementService claimManagementService;

    private final ConversionWrapperService conversionWrapperService;

    private final List<CommitterConfig.Committer> committers;

    @Transactional
    public void doCommitClaim(String partyId, long claimId, int revision) {
        log.info("Trying to commit and accept claim, partyId='{}', claimId='{}'", partyId, claimId);
        ClaimModel claimModel = claimManagementService.getClaim(partyId, claimId);

        if (revision < claimModel.getRevision()) {
            log.warn("Ignore old claim revision partyId='{}', claimId='{}'", partyId, claimId);
            return;
        }

        if (claimModel.getRevision() != revision) {
            throw new InvalidRevisionException(
                    String.format("Invalid claim revision, expected='%s', actual='%s'",
                            claimModel.getRevision(), revision)
            );
        }

        if (claimModel.getClaimStatus().getClaimStatusEnum() != ClaimStatusEnum.pending_acceptance) {
            throw new InvalidClaimStatusException(
                    String.format("Invalid claim status, expected='%s', actual='%s'",
                            ClaimStatusEnum.pending_acceptance, claimModel.getClaimStatus().getClaimStatusEnum()),
                    claimModel.getClaimStatus()
            );
        }

        new WFlow().createServiceFork(
                () -> {
                    addUserInfoFromLastModification(claimModel);
                    Claim claim = conversionWrapperService.convertClaim(claimModel);
                    try {
                        committers.forEach(committer -> sendAccept(partyId, claim, committer));
                        claimManagementService.acceptClaim(partyId, claimId, revision);
                        committers.forEach(committer -> sendCommit(partyId, claim, committer));
                        log.info("Claim have been commited and accepted, partyId='{}', claimId='{}'", partyId, claimId);
                    } catch (InvalidChangesetException ex) {
                        log.warn(
                                "An invalid changeset occurred while trying to apply the claim, " +
                                        "rollback to pending status",
                                ex);
                        claimManagementService.failClaimAcceptance(partyId, claimId, revision);
                    } catch (Exception ex) {
                        log.warn("Exception during accept and commit claim. partyId={}; claimId={}",
                                partyId, claimId, ex);
                        claimManagementService.failClaimAcceptance(partyId, claimId, revision);
                    }
                }
        ).run();
    }

    private void addUserInfoFromLastModification(ClaimModel claimModel) {
        ModificationModel modificationModel =
                claimModel.getModifications().get(claimModel.getModifications().size() - 1);
        ContextUtil.addUserInfoToContext(modificationModel.getUserInfo());
    }

    @SneakyThrows
    public void sendAccept(String partyId, Claim claim, CommitterConfig.Committer committer) {
        log.info("Trying to accept claim in service, serviceId='{}', partyId='{}', claimId='{}'",
                committer.getId(), partyId, claim.getId());
        try {
            buildClaimCommitterClient(committer).accept(partyId, claim);
            log.info("Claim have been accepted in service, serviceId='{}', partyId='{}', claimId='{}'",
                    committer.getId(), partyId, claim.getId());
        } catch (InvalidChangeset ex) {
            List<ModificationModel> invalidModifications =
                    conversionWrapperService.convertModifications(ex.getInvalidChangeset());
            throw new InvalidChangesetException(
                    String.format(
                            "Failed to accept claim in service, serviceId='%s', partyId='%s', claimId='%d', " +
                                    "reason='%s', invalidModifications='%s'",
                            committer.getId(),
                            partyId,
                            claim.getId(),
                            ex.getReason(),
                            invalidModifications
                    ),
                    invalidModifications,
                    JsonUtil.toJson(ex.getReason())
            );
        }
    }

    @SneakyThrows
    public void sendCommit(String partyId, Claim claim, CommitterConfig.Committer committer) {
        log.info("Trying to commit claim in service, serviceId='{}', partyId='{}' claimId='{}'",
                committer.getId(), partyId, claim.getId());
        buildClaimCommitterClient(committer).commit(partyId, claim);
        log.info("Claim have been commited in service, serviceId='{}', partyId='{}', claimId='{}'",
                committer.getId(), partyId, claim.getId());
    }

    private ClaimCommitterSrv.Iface buildClaimCommitterClient(CommitterConfig.Committer committer) {
        return new THSpawnClientBuilder()
                .withAddress(committer.getUri())
                .withNetworkTimeout(committer.getTimeout())
                .build(ClaimCommitterSrv.Iface.class);
    }

}
