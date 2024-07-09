package dev.vality.cm.service;

import dev.vality.cm.exception.InvalidClaimStatusException;
import dev.vality.cm.model.ClaimModel;
import dev.vality.cm.model.ClaimStatusModel;
import dev.vality.cm.model.MetadataModel;
import dev.vality.cm.model.ModificationModel;
import dev.vality.damsel.claim_management.Claim;
import dev.vality.damsel.claim_management.ClaimStatus;
import dev.vality.damsel.claim_management.Modification;
import dev.vality.damsel.msgpack.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class ConversionWrapperService {

    private final ConversionService conversionService;

    public List<ModificationModel> convertModifications(List<Modification> changeset) {
        return changeset.stream()
                .filter(ConversionWrapperService::filterUnusedModification) // TODO
                .map(change -> conversionService.convert(change, ModificationModel.class))
                .toList();
    }

    private static boolean filterUnusedModification(Modification modification) {
        return !(modification.isSetPartyModification()
                && modification.getPartyModification().isSetContractorModification()
                && modification.getPartyModification().getContractorModification().isSetModification()
                && modification.getPartyModification().getContractorModification().getModification().isSetCreation()
                && modification.getPartyModification().getContractorModification().getModification().getCreation()
                .isSetDummyEntity())
                &&
                !(modification.isSetPartyModification()
                        && modification.getPartyModification().isSetShopModification()
                        && modification.getPartyModification().getShopModification().isSetModification()
                        && modification.getPartyModification().getShopModification().getModification()
                        .isSetTurnoverLimitsModification());
    }

    public List<Modification> convertModificationModels(List<ModificationModel> modifications) {
        return modifications.stream()
                .map(modification -> conversionService.convert(modification, Modification.class))
                .toList();
    }

    public Claim convertClaim(ClaimModel claimModel) {
        return conversionService.convert(claimModel, Claim.class);
    }

    public ClaimStatus convertClaimStatus(ClaimStatusModel claimStatusModel) {
        return conversionService.convert(claimStatusModel, ClaimStatus.class);
    }

    public ClaimStatus convertClaimStatus(InvalidClaimStatusException ex) {
        return conversionService.convert(ex.getClaimStatusModel(), ClaimStatus.class);
    }

    public Value convertMsgpackValue(MetadataModel metadataModel) {
        return conversionService.convert(metadataModel, Value.class);
    }

    public MetadataModel convertMetadataModel(String key, Value value) {
        return conversionService.convert(Map.entry(key, value), MetadataModel.class);
    }

}
