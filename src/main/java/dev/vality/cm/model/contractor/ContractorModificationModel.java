package dev.vality.cm.model.contractor;

import dev.vality.cm.model.PartyModificationModel;
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
public class ContractorModificationModel extends PartyModificationModel {

    @NotNull
    @Column(nullable = false)
    private String contractorId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractorModificationModel
                && contractorId.equals(((ContractorModificationModel) that).getContractorId());
    }

}
