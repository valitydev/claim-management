package dev.vality.cm.model;

import lombok.Data;

import javax.persistence.*;
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
