package dev.vality.cm.model;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
public class LegalAgreementModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private Instant signedAt;

    @NotNull
    @Column(nullable = false)
    private String legalAgreementId;

    private Instant validUntil;

}
