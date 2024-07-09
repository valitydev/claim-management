package dev.vality.cm.model.contractor;

import dev.vality.cm.model.LegalEntityModel;
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
public class ContractorLegalEntityCreationModificationModel extends ContractorCreationModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "legal_entity_id", referencedColumnName = "id")
    private LegalEntityModel legalEntity;

    @Override
    public boolean canEqual(final Object that) {
        return super.canEqual(that);
    }

}
