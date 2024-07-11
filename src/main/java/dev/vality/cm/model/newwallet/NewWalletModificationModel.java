package dev.vality.cm.model.newwallet;

import dev.vality.cm.model.ModificationModel;
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
public class NewWalletModificationModel extends ModificationModel {

    @NotNull
    @Column(nullable = false)
    private String newWalletId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof NewWalletModificationModel
                && newWalletId.equals(((NewWalletModificationModel) that).getNewWalletId());
    }
}
