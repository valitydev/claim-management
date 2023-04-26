package dev.vality.cm.model.newwallet;

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
