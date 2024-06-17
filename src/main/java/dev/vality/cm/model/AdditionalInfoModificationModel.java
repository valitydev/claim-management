package dev.vality.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class AdditionalInfoModificationModel extends PartyModificationModel {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String partyName;

    @Column
    private String managerContactEmails;

    @Column
    private String comment;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof AdditionalInfoModificationModel;
    }

}
