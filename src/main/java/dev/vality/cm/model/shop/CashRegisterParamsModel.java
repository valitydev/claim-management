package dev.vality.cm.model.shop;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
public class CashRegisterParamsModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private int providerId;

    @NotNull
    @Column(nullable = false)
    private byte[] providerParams;

}
