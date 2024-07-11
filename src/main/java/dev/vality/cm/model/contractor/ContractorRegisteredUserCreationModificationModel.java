package dev.vality.cm.model.contractor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractorRegisteredUserCreationModificationModel extends ContractorCreationModificationModel {

    @NotNull
    @Column(nullable = false)
    private String email;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractorRegisteredUserCreationModificationModel
                && super.canEqual(that);
    }

}
