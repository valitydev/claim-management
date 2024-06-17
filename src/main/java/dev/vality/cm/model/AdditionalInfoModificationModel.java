package dev.vality.cm.model;

import dev.vality.damsel.claim_management.AdditionalInfoModificationUnit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @Column(columnDefinition = "text[]")
    @Type(type = "com.thorben.janssen.PostgreSqlStringArrayType")
    private List<String> managerContactEmails;

    @Column
    private String comment;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof AdditionalInfoModificationModel;
    }

}
