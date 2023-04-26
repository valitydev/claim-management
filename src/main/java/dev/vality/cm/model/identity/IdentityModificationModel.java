package dev.vality.cm.model.identity;

import dev.vality.cm.model.ModificationModel;
import dev.vality.cm.model.newwallet.NewWalletModificationModel;
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
public class IdentityModificationModel extends ModificationModel {

    @NotNull
    @Column(nullable = false)
    private String identityId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof IdentityModificationModel
                && identityId.equals(((IdentityModificationModel) that).getIdentityId());
    }
}
