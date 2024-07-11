package dev.vality.cm.model.wallet;

import dev.vality.cm.model.WalletAccountParamsModel;
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
public class WalletAccountCreationModificationModel extends WalletModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "wallet_account_params_id", referencedColumnName = "id")
    private WalletAccountParamsModel walletAccountParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof WalletAccountCreationModificationModel
                && super.canEqual(that);
    }

}
