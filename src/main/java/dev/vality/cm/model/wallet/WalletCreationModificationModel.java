package dev.vality.cm.model.wallet;

import dev.vality.cm.model.WalletParamsModel;
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
public class WalletCreationModificationModel extends WalletModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "wallet_params_id", referencedColumnName = "id")
    private WalletParamsModel walletParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof WalletCreationModificationModel
                && super.canEqual(that);
    }

}
