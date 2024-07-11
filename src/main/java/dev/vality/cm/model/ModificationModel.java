package dev.vality.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
@Entity
@ToString
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause = "deleted <> true")
public class ModificationModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_info_id", referencedColumnName = "id")
    private UserInfoModel userInfo;

    @Column(nullable = true, updatable = true)
    private Instant removedAt;

    @Column(nullable = true, updatable = true)
    private Instant changedAt;

    @NotNull
    private Boolean deleted = false;

    public boolean canEqual(Object that) {
        return that instanceof ModificationModel;
    }

}
