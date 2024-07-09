package dev.vality.cm.model.contractor;

import dev.vality.cm.model.PrivateEntityModel;
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
public class ContractorPrivateEntityCreationModificationModel extends ContractorCreationModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "private_entity_id", referencedColumnName = "id")
    PrivateEntityModel privateEntity;

    @Override
    public boolean canEqual(final Object that) {
        return super.canEqual(that);
    }

}
