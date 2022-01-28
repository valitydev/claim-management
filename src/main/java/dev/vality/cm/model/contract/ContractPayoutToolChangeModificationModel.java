package dev.vality.cm.model.contract;

import dev.vality.cm.model.PayoutToolInfoModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractPayoutToolChangeModificationModel extends ContractPayoutToolModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "payout_tool_info_id", referencedColumnName = "id")
    private PayoutToolInfoModel payoutToolInfoModel;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractPayoutToolChangeModificationModel
                && super.canEqual(that);
    }

}
