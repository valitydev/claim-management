package dev.vality.cm.converter;

import dev.vality.cm.model.ClaimModel;
import dev.vality.cm.model.MetadataModel;
import dev.vality.damsel.claim_management.Claim;
import dev.vality.damsel.claim_management.ClaimStatus;
import dev.vality.damsel.claim_management.ModificationUnit;
import dev.vality.damsel.msgpack.Value;
import dev.vality.geck.common.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClaimModelToClaimConverter implements ClaimConverter<ClaimModel, Claim> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Claim convert(ClaimModel claimModel) {
        ClaimStatus claimStatus = conversionService.convert(claimModel.getClaimStatus(), ClaimStatus.class);
        return new Claim()
                .setId(claimModel.getId())
                .setPartyId(claimModel.getPartyId())
                .setCreatedAt(TypeUtil.temporalToString(claimModel.getCreatedAt()))
                .setUpdatedAt(
                        Optional.ofNullable(claimModel.getUpdatedAt())
                                .map(TypeUtil::temporalToString)
                                .orElse(null)
                )
                .setStatus(claimStatus)
                .setRevision(claimModel.getRevision())
                .setChangeset(
                        claimModel.getModifications().stream()
                                .map(modificationModel -> conversionService
                                        .convert(modificationModel, ModificationUnit.class))
                                .toList()
                ).setMetadata(
                        Optional.ofNullable(claimModel.getMetadata())
                                .filter(metadataModels -> !metadataModels.isEmpty())
                                .map(
                                        metadata -> metadata.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                MetadataModel::getKey,
                                                                metadataModel -> conversionService
                                                                        .convert(metadataModel, Value.class)
                                                        )
                                                )
                                )
                                .orElse(null)
                );
    }
}
