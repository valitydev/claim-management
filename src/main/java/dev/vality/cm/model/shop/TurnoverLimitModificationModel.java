package dev.vality.cm.model.shop;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
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

    @ManyToOne
    @JoinColumn(name = "turnover_limits_id", nullable = false)
    private TurnoverLimitsModificationModel limit;
}
