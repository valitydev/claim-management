package dev.vality.cm.model.wallet;

import dev.vality.cm.model.PartyModificationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class WalletModificationModel extends PartyModificationModel {

    @NotNull
    @Column(nullable = false)
    private String walletId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof WalletModificationModel
                && walletId.equals(((WalletModificationModel) that).getWalletId());
    }

}
