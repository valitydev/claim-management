package dev.vality.cm.model.newwallet;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class NewWalletParamsModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private String identityId;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String currency;

    private String metadata;

}
