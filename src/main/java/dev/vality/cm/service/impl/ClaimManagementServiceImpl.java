package dev.vality.cm.service.impl;

import dev.vality.cm.exception.*;
import dev.vality.cm.model.*;
import dev.vality.cm.model.*;
import dev.vality.cm.model.status.StatusModificationModel;
import dev.vality.cm.model.status.StatusModificationTypeEnum;
import dev.vality.cm.repository.ClaimRepository;
import dev.vality.cm.repository.ModificationRepository;
import dev.vality.cm.search.ClaimPageSearchParameters;
import dev.vality.cm.search.ClaimPageSearchRequest;
import dev.vality.cm.search.ClaimPageSearchResponse;
import dev.vality.cm.service.ClaimManagementService;
import dev.vality.cm.service.ContinuationTokenService;
import dev.vality.cm.service.ConversionWrapperService;
import dev.vality.cm.util.ClaimEventFactory;
import dev.vality.cm.util.ContextUtil;
import dev.vality.damsel.claim_management.Claim;
import dev.vality.damsel.claim_management.Event;
import dev.vality.damsel.claim_management.Modification;
import dev.vality.damsel.claim_management.ModificationChange;
import dev.vality.cm.exception.*;
import dev.vality.cm.repository.ClaimSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TBase;
import org.hibernate.Hibernate;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.concurrent.ListenableFuture;

import javax.transaction.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@RequiredArgsConstructor
public class ClaimManagementServiceImpl implements ClaimManagementService {

    private final ContinuationTokenService continuationTokenService;

    private final ConversionWrapperService conversionWrapperService;

    private final ConversionService conversionService;

    private final ClaimRepository claimRepository;

    private final ModificationRepository modificationRepository;

    private final ClaimEventFactory claimEventFactory;

    private final KafkaTemplate<String, TBase> kafkaTemplate;

    private final RetryTemplate retryTemplate;

    private final String eventSinkTopic;

    @Override
    @Transactional
    public Claim createClaim(String partyId, List<Modification> changeset) {
        log.info("Trying to create new claim, partyId='{}', modifications='{}'", partyId, changeset);

        List<ModificationModel> modifications = conversionWrapperService.convertModifications(changeset);

        checkEquals(modifications, modifications);

        ClaimStatusModel claimStatusModel = new ClaimStatusModel();
        claimStatusModel.setClaimStatusEnum(ClaimStatusEnum.pending);

        ClaimModel claimModel = new ClaimModel();
        claimModel.setPartyId(partyId);
        claimModel.setClaimStatus(claimStatusModel);

        modifications.forEach(this::addUserInfo);
        claimModel.setModifications(modifications);


        claimModel = claimRepository.saveAndFlush(claimModel);
        System.out.println("claimModel after flush " + claimModel);

        ClaimModel claim1 = getClaim(partyId, claimModel.getId());
        System.out.println("claimModel getClaim " + claim1);

        Claim claim = conversionWrapperService.convertClaim(claimModel);

        System.out.println("claimModel after flush conversion " + claim);
        System.out.println("claimModel getClaim  conversion " + conversionWrapperService.convertClaim(claim1));


        Event claimEvent = claimEventFactory.createCreatedClaimEvent(partyId, changeset, claim);

        sendToEventSinkWithRetry(partyId, claimEvent);

        log.info("Claim have been created, partyId='{}', claim='{}'", partyId, claim);

        return claim;
    }

    @Override
    @Transactional
    public void updateClaim(String partyId, long claimId, int revision, List<Modification> changeset) {
        log.info("Trying to update claim, partyId='{}', claimId='{}', revision='{}', modifications='{}'", partyId,
                claimId, revision, changeset);

        List<ModificationModel> modifications = conversionWrapperService.convertModifications(changeset);

        checkEquals(modifications, modifications);

        ClaimModel claimModel = getClaim(partyId, claimId, false);

        checkRevision(claimModel, revision);
        checkStatus(claimModel, Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review));
        checkForConflicts(claimModel.getModifications(), modifications);
        modifications.forEach(this::addUserInfo);
        claimModel.getModifications().addAll(modifications);

        claimModel = claimRepository.saveAndFlush(claimModel);

        Event claimEvent = claimEventFactory
                .createUpdateClaimEvent(partyId, claimId, claimModel.getRevision(), changeset,
                        claimModel.getUpdatedAt());

        sendToEventSinkWithRetry(partyId, claimEvent);

        log.info("Claim have been updated, partyId='{}', claim='{}'", partyId, claimModel);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ClaimModel getClaim(String partyId, long claimId) {
        log.info("Trying to get Claim, partyId={}, claimId={}", partyId, claimId);

        ClaimModel claimModel = getClaim(partyId, claimId, true);

        log.info("Claim has been got, partyId={}, claimId={}, claimModel={}", partyId, claimId, claimModel);
        return claimModel;
    }

    private ClaimModel getClaim(String partyId, long claimId, boolean needInitialize) {
        ClaimModel claimModel = claimRepository.findOne(
                where(ClaimSpecifications.equalsByPartyIdAndClaimId(partyId, claimId))
        )
                .orElseThrow(ClaimNotFoundException::new);

        if (needInitialize) {
            initializeClaim(claimModel);
        }

        return claimModel;
    }

    @Override
    @Transactional
    public ClaimModel pendingAcceptanceClaim(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.pending_acceptance, null),
                Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review)
        );
    }

    @Override
    @Transactional
    public ClaimModel failClaimAcceptance(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.pending, null),
                Collections.singletonList(ClaimStatusEnum.pending_acceptance)
        );
    }

    @Override
    @Transactional
    public ClaimModel acceptClaim(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.accepted, null),
                Arrays.asList(ClaimStatusEnum.pending_acceptance)
        );
    }

    @Override
    @Transactional
    public ClaimModel revokeClaim(String partyId, long claimId, int revision, String reason) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.revoked, reason),
                Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review)
        );
    }

    @Override
    @Transactional
    public ClaimModel denyClaim(String partyId, long claimId, int revision, String reason) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.denied, reason),
                Arrays.asList(ClaimStatusEnum.pending, ClaimStatusEnum.review)
        );
    }

    @Override
    @Transactional
    public ClaimModel requestClaimReview(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.review, null),
                Collections.singletonList(ClaimStatusEnum.pending)
        );
    }

    @Override
    @Transactional
    public ClaimModel requestClaimChanges(String partyId, long claimId, int revision) {
        return changeStatus(
                partyId, claimId, revision,
                new ClaimStatusModel(ClaimStatusEnum.pending, null),
                Collections.singletonList(ClaimStatusEnum.review)
        );
    }

    @Override
    public ClaimModel changeStatus(String partyId, long claimId, int revision, ClaimStatusModel targetClaimStatus,
                                   List<ClaimStatusEnum> expectedStatuses) {
        log.info("Trying to change status in claim, claimId='{}', targetStatus='{}'", claimId, targetClaimStatus);

        ClaimModel claimModel = getClaim(partyId, claimId, false);

        if (claimModel.getClaimStatus().getClaimStatusEnum() == targetClaimStatus.getClaimStatusEnum()) {
            log.info("Claim is already in target status, status='{}'", targetClaimStatus);
            return claimModel;
        }

        checkRevision(claimModel, revision);
        checkStatus(claimModel, expectedStatuses);

        claimModel.setClaimStatus(targetClaimStatus);

        StatusModificationModel statusModificationModel = new StatusModificationModel();
        statusModificationModel.setClaimStatus(targetClaimStatus);
        statusModificationModel.setStatusModificationType(StatusModificationTypeEnum.change);
        statusModificationModel.setUserInfo(ContextUtil.getUserInfoFromContext());
        claimModel.getModifications().add(statusModificationModel);

        claimModel = claimRepository.saveAndFlush(claimModel);

        Event claimEvent = claimEventFactory.createChangeStatusEvent(
                partyId, claimId, claimModel.getRevision(),
                conversionWrapperService.convertClaimStatus(claimModel.getClaimStatus()),
                claimModel.getUpdatedAt()
        );

        sendToEventSinkWithRetry(partyId, claimEvent);
        log.info("Status in claim have been changed, claimId='{}', targetStatus='{}'", claimId, targetClaimStatus);

        return claimModel;
    }

    @Override
    @Transactional
    public ClaimPageSearchResponse searchClaims(ClaimPageSearchRequest claimSearchRequest, String continuationToken,
                                                int limit) {
        List<Object> parameters = Arrays.asList(claimSearchRequest, limit);
        ClaimPageSearchParameters claimSearchParameters = new ClaimPageSearchParameters(0, limit);
        if (continuationToken != null) {
            int pageNumber = continuationTokenService.validateAndGet(continuationToken, Integer.class, parameters);
            claimSearchParameters.setPage(pageNumber);
        }

        Page<ClaimModel> claimsPage = searchClaims(claimSearchRequest, claimSearchParameters);

        return new ClaimPageSearchResponse(
                claimsPage.getContent(),
                claimsPage.hasNext() ? continuationTokenService
                        .buildToken(claimsPage.getPageable().next().getPageNumber(), parameters) : null
        );
    }

    @Override
    @Transactional
    public Page<ClaimModel> searchClaims(ClaimPageSearchRequest claimSearchRequest,
                                         ClaimPageSearchParameters claimSearchParameters) {
        log.info("Trying to search claims, claimSearchRequest='{}', claimSearchParameters='{}'",
                claimSearchRequest, claimSearchParameters);

        Page<ClaimModel> claims = claimRepository.findAll(
                ClaimSpecifications.equalsByPartyIdClaimIdEmailAndStatusIn(
                        claimSearchRequest.getPartyId(),
                        claimSearchRequest.getClaimId(),
                        claimSearchRequest.getEmail(),
                        claimSearchRequest.getStatuses()
                ),
                PageRequest.of(claimSearchParameters.getPage(), claimSearchParameters.getLimit(), Sort.Direction.DESC,
                        "id")
        );

        claims.getContent().forEach(this::initializeClaim);

        log.info("{} claims have been found", claims.getTotalElements());

        return claims;
    }

    @Override
    @Transactional
    public MetadataModel getMetadata(String partyId, long claimId, String key) {
        log.info("Trying to get metadata field, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);

        ClaimModel claimModel = getClaim(partyId, claimId, false);

        MetadataModel metadataModel = claimModel.getMetadata().stream()
                .filter(metadata -> key.equals(metadata.getKey()))
                .findFirst()
                .orElseThrow(MetadataKeyNotFoundException::new);

        log.info("Metadata field have been found, metadata='{}'", metadataModel);

        return metadataModel;
    }

    @Override
    @Transactional
    public void setMetadata(String partyId, long claimId, String key, MetadataModel metadataModel) {
        log.info("Trying to change metadata field, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);

        ClaimModel claimModel = getClaim(partyId, claimId, false);
        claimModel.getMetadata().removeIf(metadata -> key.equals(metadata.getKey()));
        claimModel.getMetadata().add(metadataModel);

        claimRepository.save(claimModel);

        log.info("metadata field have been changed, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);
    }

    @Override
    @Transactional
    public void removeMetadata(String partyId, long claimId, String key) {
        log.info("Trying to remove metadata field, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);

        ClaimModel claimModel = getClaim(partyId, claimId, false);
        claimModel.getMetadata().removeIf(metadata -> key.equals(metadata.getKey()));

        claimRepository.save(claimModel);

        log.info("metadata field have been removed, partyId='{}', claimId='{}', key='{}'", partyId, claimId, key);
    }

    @Override
    @Transactional
    public void updateModification(String partyId, long claimId, int revision, long modificationId,
                                   ModificationChange modificationChange) {
        log.info("Update modification by partyId='{}', claimId='{}', revision='{}'", partyId, claimId, revision);

        ClaimModel claimModel = getClaim(partyId, claimId, false);
        checkRevision(claimModel, revision);

        ModificationModel modificationModel = claimModel.getModifications().stream()
                .filter(mod -> mod.getId() == modificationId).findFirst()
                .orElseThrow(() -> new ModificationNotFoundException(modificationId));

        ModificationModel modificationChangeModel =
                conversionService.convert(modificationChange, ModificationModel.class);
        if (modificationChangeModel == null) {
            throw new IllegalStateException("ModificationModel can't be null");
        }

        if (!modificationModel.getClass().equals(modificationChangeModel.getClass())) {
            log.warn("Wrong modification type: {}. Expected type: {}",
                    modificationChangeModel.getClass(), modificationModel.getClass());
            throw new ModificationWrongTypeException();
        }

        modificationChangeModel.setId(modificationId);
        modificationChangeModel.setUserInfo(modificationModel.getUserInfo());
        modificationChangeModel.setChangedAt(Instant.now());

        modificationRepository.save(modificationChangeModel);

        log.info("Modification has been successfully update: {}", modificationId);
    }

    @Override
    @Transactional
    public void removeModification(String partyId, long claimId, int revision, long modificationId) {
        log.info("Remove modification by partyId='{}', claimId='{}', revision='{}', modificationId='{}'",
                partyId, claimId, revision, modificationId);

        ClaimModel claimModel = getClaim(partyId, claimId, false);
        checkRevision(claimModel, revision);
        ModificationModel modificationModel = claimModel.getModifications().stream()
                .filter(mod -> mod.getId() == modificationId)
                .findFirst()
                .orElseThrow(() -> new ModificationNotFoundException(modificationId));
        modificationModel.setDeleted(true);
        modificationModel.setRemovedAt(Instant.now());

        log.info("Modification has been successfully removed: {}", modificationId);
    }



    private void checkForConflicts(List<ModificationModel> oldModifications, List<ModificationModel> newModifications) {
        for (ModificationModel newModification : newModifications) {
            for (ModificationModel oldModification : oldModifications) {
                if (oldModification.canEqual(newModification)) {
                    throw new ChangesetConflictException(
                            String.format("Found conflict in modifications, oldModification='%s', newModification='%s'",
                                    oldModification, newModification),
                            oldModification.getId()
                    );
                }
            }
        }
    }

    private void checkStatus(ClaimModel claimModel, List<ClaimStatusEnum> expectedStatuses) {
        if (!expectedStatuses.isEmpty()
                && !expectedStatuses.contains(claimModel.getClaimStatus().getClaimStatusEnum())) {
            throw new InvalidClaimStatusException(
                    String.format("Invalid claim status, expected='%s', actual='%s'",
                            expectedStatuses, claimModel.getClaimStatus().getClaimStatusEnum()),
                    claimModel.getClaimStatus()
            );
        }
    }

    private void checkRevision(ClaimModel claimModel, int revision) {
        if (claimModel.getRevision() != revision) {
            throw new InvalidRevisionException(
                    String.format("Invalid claim revision, expected='%s', actual='%s'",
                            claimModel.getRevision(), revision)
            );
        }
    }

    private void checkEquals(List<ModificationModel> oldModifications, List<ModificationModel> newModifications) {
        List<ModificationModel> conflictModifications = new ArrayList<>();

        for (int i = 0; i < newModifications.size() - 1; i++) {
            for (int j = i + 1; j < oldModifications.size(); j++) {
                if (newModifications.get(i).canEqual(oldModifications.get(j))) {
                    conflictModifications.add(oldModifications.get(j));
                }
            }
        }

        if (!conflictModifications.isEmpty()) {
            throw new InvalidChangesetException(
                    String.format("ModificationModels contains doubles, count=%s", conflictModifications.size()),
                    conflictModifications,
                    null
            );
        }
    }

    private void initializeClaim(ClaimModel claimModel) {
        Hibernate.initialize(claimModel.getModifications());
        Hibernate.initialize(claimModel.getMetadata());
    }

    private void addUserInfo(ModificationModel modificationModel) {
        modificationModel.setUserInfo(ContextUtil.getUserInfoFromContext());
    }

    private void sendToEventSinkWithRetry(String partyId, Event event) {
        retryTemplate.execute(
                retryCallback -> {
                    try {
                        log.info("Trying to send Event to kafka, partyId={}, event={}", partyId, event);

                        ListenableFuture<SendResult<String, TBase>> future =
                                kafkaTemplate.send(eventSinkTopic, partyId, event);

                        future.get();

                        log.info("Event has been sent to kafka, partyId={}, event={}", partyId, event);
                    } catch (InterruptedException e) {
                        log.error("Error when sendToEventSinkWithRetry partyId: {}, event: {}", partyId, event, e);
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException e) {
                        log.error("Error when sendToEventSinkWithRetry partyId: {}, event: {}", partyId, event, e);
                        throw new RuntimeException("Error when sendToEventSinkWithRetry", e);
                    }
                    return null;
                }
        );
    }
}
