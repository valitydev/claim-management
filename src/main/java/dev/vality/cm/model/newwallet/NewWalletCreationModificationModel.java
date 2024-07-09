package dev.vality.cm.model.newwallet;

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
public class NewWalletCreationModificationModel extends NewWalletModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "new_wallet_params_id", referencedColumnName = "id")
    private NewWalletParamsModel newWalletParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof NewWalletCreationModificationModel
                && super.canEqual(that);
    }

}
