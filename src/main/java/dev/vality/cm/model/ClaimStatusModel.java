package dev.vality.cm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ClaimStatusModel {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "claim_status", nullable = false)
    private ClaimStatusEnum claimStatusEnum;

    private String claimStatusReason;

}
