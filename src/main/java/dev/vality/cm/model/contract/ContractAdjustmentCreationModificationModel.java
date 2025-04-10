package dev.vality.cm.model.contract;

import dev.vality.cm.model.ContractAdjustmentParamsModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@OnDelete(action = OnDeleteAction.CASCADE)
public class ContractAdjustmentCreationModificationModel extends ContractAdjustmentModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "contract_adjustment_params_id", referencedColumnName = "id")
    private ContractAdjustmentParamsModel contractAdjustmentParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractAdjustmentCreationModificationModel
                && super.canEqual(that);
    }

}
