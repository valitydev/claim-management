package dev.vality.cm.model;

import dev.vality.damsel.claim_management.AdditionalInfoModificationUnit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class AdditionalInfoModificationModel extends PartyModificationModel {

    @Column
    private String partyName;

    @Column
    private List<String> managerContactEmails;

    @Column
    private String comment;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof AdditionalInfoModificationModel;
    }

}
