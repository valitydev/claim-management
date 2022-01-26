package dev.vality.cm.model.contractor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

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
