package dev.vality.cm.model.identity;

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
public class IdentityCreationModificationModel extends IdentityModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "identity_params_id", referencedColumnName = "id")
    private IdentityParamsModel identityParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof IdentityCreationModificationModel
                && super.canEqual(that);
    }

}
