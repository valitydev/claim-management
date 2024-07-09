package dev.vality.cm.model.contract;

import dev.vality.cm.model.ContractParamsModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractCreationModificationModel extends ContractModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "contract_params_id", referencedColumnName = "id")
    private ContractParamsModel contractParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractCreationModificationModel
                && super.canEqual(that);
    }

}
