package dev.vality.cm.handler;

import dev.vality.cm.exception.*;
import dev.vality.cm.model.ClaimModel;
import dev.vality.cm.model.ClaimStatusEnum;
import dev.vality.cm.model.MetadataModel;
import dev.vality.cm.search.ClaimPageSearchRequest;
import dev.vality.cm.search.ClaimPageSearchResponse;
import dev.vality.cm.service.ClaimManagementService;
import dev.vality.cm.service.ConversionWrapperService;
import dev.vality.cm.util.JsonUtil;
import dev.vality.damsel.claim_management.*;
import dev.vality.damsel.msgpack.Value;
import dev.vality.geck.common.util.TBaseUtil;
import dev.vality.woody.api.flow.error.WUndefinedResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class ClaimManagementHandler implements ClaimManagementSrv.Iface {

    private final ClaimManagementService claimManagementService;

    private final ConversionWrapperService conversionWrapperService;

    private final long limit;

    @Override
    public Claim createClaim(String partyId, List<Modification> changeset) throws TException {
        try {
            return claimManagementService.createClaim(partyId, changeset);
        } catch (InvalidChangesetException ex) {
            log.warn(ex.getMessage(), ex);
            throw new InvalidChangeset()
                    .setReason(JsonUtil.toTBase(ex.getInvalidChangesetReasonJson(), InvalidChangesetReason.class))
                    .setInvalidChangeset(conversionWrapperService.convertModificationModels(ex.getModifications()));
        } catch (Exception ex) {
            throw undefinedResultException(ex, "createClaim");
        }
    }

    @Override
    public Claim getClaim(String partyId, long claimId) throws TException {
        try {
            ClaimModel claimModel = claimManagementService.getClaim(partyId, claimId);
            return conversionWrapperService.convertClaim(claimModel);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (Exception ex) {
            throw undefinedResultException(ex, "getClaim");
        }
    }

    @Override
    public ClaimSearchResponse searchClaims(ClaimSearchQuery claimRequest)
            throws TException {
        try {
            if (claimRequest.getLimit() > limit) {
                throw new LimitExceededException(
                        String.format("Limit from request '%s more than can be by service '%s'",
                                claimRequest.getLimit(), limit));
            }

            List<ClaimStatusEnum> claimStatusEnums = Optional.ofNullable(claimRequest.getStatuses())
                    .map(
                            statuses -> statuses.stream()
                                    .map(status -> TBaseUtil.unionFieldToEnum(status, ClaimStatusEnum.class))
                                    .collect(Collectors.toList())
                    )
                    .orElse(null);

            ClaimPageSearchResponse claimsWithContinuationToken = claimManagementService.searchClaims(
                    ClaimPageSearchRequest.builder()
                            .partyId(claimRequest.getPartyId())
                            .claimId(claimRequest.isSetClaimId() ? claimRequest.getClaimId() : null)
                            .email(claimRequest.getEmail())
                            .statuses(claimStatusEnums)
                            .build(),
                    claimRequest.getContinuationToken(),
                    claimRequest.getLimit()
            );

            return new ClaimSearchResponse()
                    .setResult(
                            claimsWithContinuationToken.getClaims().stream()
                                    .map(conversionWrapperService::convertClaim)
                                    .collect(Collectors.toList())
                    )
                    .setContinuationToken(claimsWithContinuationToken.getToken());
        } catch (BadContinuationTokenException ex) {
            log.warn(String.format("Error then '%s'", "searchClaims"), ex);
            throw new BadContinuationToken(ex.getMessage());
        } catch (LimitExceededException ex) {
            log.warn(ex.getMessage(), ex);
            throw new LimitExceeded(ex.getMessage());
        } catch (Exception ex) {
            throw undefinedResultException(ex, "searchClaims");
        }
    }

    @Override
    public void acceptClaim(String partyId, long claimId, int revision)
            throws TException {
        try {
            claimManagementService.pendingAcceptanceClaim(partyId, claimId, revision);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (InvalidRevisionException ex) {
            throw invalidClaimRevision(ex, "acceptClaim");
        } catch (InvalidClaimStatusException ex) {
            throw invalidClaimStatus(ex, "acceptClaim");
        } catch (Exception ex) {
            throw undefinedResultException(ex, "acceptClaim");
        }
    }

    @Override
    public void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset)
            throws
            TException {
        try {
            claimManagementService.updateClaim(partyId, claimId, revision, changeset);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (InvalidRevisionException ex) {
            throw invalidClaimRevision(ex, "updateClaim");
        } catch (InvalidClaimStatusException ex) {
            throw invalidClaimStatus(ex, "updateClaim");
        } catch (ChangesetConflictException ex) {
            log.warn(ex.getMessage(), ex);
            throw new ChangesetConflict(ex.getConflictedId());
        } catch (InvalidChangesetException ex) {
            log.warn(ex.getMessage(), ex);
            throw new InvalidChangeset()
                    .setReason(JsonUtil.toTBase(ex.getInvalidChangesetReasonJson(), InvalidChangesetReason.class))
                    .setInvalidChangeset(conversionWrapperService.convertModificationModels(ex.getModifications()));
        } catch (Exception ex) {
            throw undefinedResultException(ex, "updateClaim");
        }
    }

    @Override
    public void updateModification(String partyId,
                                   long id,
                                   int revision,
                                   long modificationId,
                                   ModificationChange modificationChange) throws TException {
        try {
            claimManagementService.updateModification(partyId, id, revision, modificationId, modificationChange);
        } catch (ModificationNotFoundException ex) {
            throw modificationNotFound(ex);
        } catch (ModificationWrongTypeException ex) {
            throw modificationWrongType(modificationId, ex);
        } catch (InvalidRevisionException ex) {
            throw invalidClaimRevision(ex, "updateModification");
        } catch (Exception ex) {
            throw undefinedResultException(ex, "updateModification");
        }
    }

    @Override
    public void removeModification(String partyId, long id, int revision, long modificationId) throws TException {
        try {
            claimManagementService.removeModification(partyId, id, revision, modificationId);
        } catch (ModificationNotFoundException ex) {
            throw modificationNotFound(ex);
        } catch (Exception ex) {
            throw undefinedResultException(ex, "removeModification");
        }
    }

    @Override
    public void requestClaimReview(String partyId, long claimId, int revision)
            throws TException {
        try {
            claimManagementService.requestClaimReview(partyId, claimId, revision);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (InvalidRevisionException ex) {
            throw invalidClaimRevision(ex, "requestClaimReview");
        } catch (InvalidClaimStatusException ex) {
            throw invalidClaimStatus(ex, "requestClaimReview");
        } catch (Exception ex) {
            throw undefinedResultException(ex, "requestClaimReview");
        }
    }

    @Override
    public void requestClaimChanges(String partyId, long claimId, int revision)
            throws TException {
        try {
            claimManagementService.requestClaimChanges(partyId, claimId, revision);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (InvalidRevisionException ex) {
            throw invalidClaimRevision(ex, "requestClaimChanges");
        } catch (InvalidClaimStatusException ex) {
            throw invalidClaimStatus(ex, "requestClaimChanges");
        } catch (Exception ex) {
            throw undefinedResultException(ex, "requestClaimChanges");
        }
    }

    @Override
    public void denyClaim(String partyId, long claimId, int revision, String reason)
            throws TException {
        try {
            claimManagementService.denyClaim(partyId, claimId, revision, reason);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (InvalidRevisionException ex) {
            throw invalidClaimRevision(ex, "denyClaim");
        } catch (InvalidClaimStatusException ex) {
            throw invalidClaimStatus(ex, "denyClaim");
        } catch (Exception ex) {
            throw undefinedResultException(ex, "denyClaim");
        }
    }

    @Override
    public void revokeClaim(String partyId, long claimId, int revision, String reason)
            throws TException {
        try {
            claimManagementService.revokeClaim(partyId, claimId, revision, reason);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (InvalidRevisionException ex) {
            throw invalidClaimRevision(ex, "revokeClaim");
        } catch (InvalidClaimStatusException ex) {
            throw invalidClaimStatus(ex, "revokeClaim");
        } catch (Exception ex) {
            throw undefinedResultException(ex, "revokeClaim");
        }
    }

    @Override
    public Value getMetadata(String partyId, long claimId, String key)
            throws TException {
        try {
            MetadataModel metadataModel = claimManagementService.getMetadata(partyId, claimId, key);
            return conversionWrapperService.convertMsgpackValue(metadataModel);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (MetadataKeyNotFoundException ex) {
            log.warn(String.format("Error then '%s'", "getMetadata"), ex);
            throw new MetadataKeyNotFound();
        } catch (Exception ex) {
            throw undefinedResultException(ex, "getMetadata");
        }
    }

    @Override
    public void setMetadata(String partyId, long claimId, String key, Value value) throws TException {
        MetadataModel metadataModel = conversionWrapperService.convertMetadataModel(key, value);
        try {
            claimManagementService.setMetadata(partyId, claimId, key, metadataModel);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (Exception ex) {
            throw undefinedResultException(ex, "setMetadata");
        }
    }

    @Override
    public void removeMetadata(String partyId, long claimId, String key) throws TException {
        try {
            claimManagementService.removeMetadata(partyId, claimId, key);
        } catch (ClaimNotFoundException ex) {
            throw claimNotFound(claimId, ex);
        } catch (Exception ex) {
            throw undefinedResultException(ex, "removeMetadata");
        }
    }

    private InvalidClaimRevision invalidClaimRevision(InvalidRevisionException ex, String msg) {
        log.warn(String.format("Error then '%s'", msg), ex);
        return new InvalidClaimRevision();
    }

    private InvalidClaimStatus invalidClaimStatus(InvalidClaimStatusException ex, String msg) {
        log.warn(String.format("Error then '%s'", msg), ex);
        return new InvalidClaimStatus(conversionWrapperService.convertClaimStatus(ex));
    }

    private ClaimNotFound claimNotFound(long claimId, ClaimNotFoundException ex) {
        log.warn("Claim not found, claimId={}", claimId, ex);
        return new ClaimNotFound();
    }

    private ModificationNotFound modificationNotFound(ModificationNotFoundException ex) {
        log.warn("Modification not found: {}", ex.getModificationId(), ex);
        return new ModificationNotFound(ex.getModificationId());
    }

    private ModificationWrongType modificationWrongType(long modificationId, ModificationWrongTypeException ex) {
        log.warn("Modification wrong type: {}", modificationId, ex);
        return new ModificationWrongType();
    }

    private WUndefinedResultException undefinedResultException(Exception ex, String msg) {
        log.warn(String.format("Error then '%s'", msg), ex);
        return new WUndefinedResultException(msg, ex);
    }
}
