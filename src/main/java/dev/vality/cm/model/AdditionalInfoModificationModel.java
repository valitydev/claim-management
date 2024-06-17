package dev.vality.cm.model;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )})
public class AdditionalInfoModificationModel extends PartyModificationModel {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String partyName;

    @Column(columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] managerContactEmails;

    @Column
    private String comment;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof AdditionalInfoModificationModel;
    }

}
