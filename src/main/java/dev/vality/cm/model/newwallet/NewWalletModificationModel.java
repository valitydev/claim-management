package dev.vality.cm.model.newwallet;

import dev.vality.cm.model.ModificationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotNull;

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
