package dev.vality.cm.model.identity;

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
