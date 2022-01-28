package dev.vality.cm.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PayoutToolInfoModel {

    @Id
    @GeneratedValue
    private long id;

}
