package dev.vality.cm.model.contract;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractContractorChangeModificationModel extends ContractModificationModel {

    @NotNull
    @Column(nullable = false)
    private String contractorId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractContractorChangeModificationModel
                && super.canEqual(that);
    }

}
