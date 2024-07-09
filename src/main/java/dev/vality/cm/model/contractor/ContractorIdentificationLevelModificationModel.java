package dev.vality.cm.model.contractor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractorIdentificationLevelModificationModel extends ContractorModificationModel {

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractorIdentificationLevel contractorIdentificationLevel;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractorIdentificationLevelModificationModel
                && super.canEqual(that);
    }

}
