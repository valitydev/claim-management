package dev.vality.cm.model.contract;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
@OnDelete(action = OnDeleteAction.CASCADE)
public class ContractAdjustmentModificationModel extends ContractModificationModel {

    @NotNull
    @Column(nullable = false)
    private String contractAdjustmentId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractAdjustmentModificationModel
                && super.canEqual(that);
    }

}
