package dev.vality.cm.model.shop;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class TurnoverLimitModificationModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private String limitConfigId;

    @NotNull
    @Column(nullable = false)
    private Long amountUpperBoundary;

    private Long domainRevision;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "turnover_limits_id", nullable = false)
    private TurnoverLimitsModificationModel limit;
}
