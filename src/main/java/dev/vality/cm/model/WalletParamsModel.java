package dev.vality.cm.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class WalletParamsModel {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @NotNull
    @Column(nullable = false)
    private String contractId;

}
