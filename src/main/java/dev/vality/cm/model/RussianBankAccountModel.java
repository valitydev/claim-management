package dev.vality.cm.model;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
public class RussianBankAccountModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private String account;

    @NotNull
    @Column(nullable = false)
    private String bankName;

    @NotNull
    @Column(nullable = false)
    private String bankPostAccount;

    @NotNull
    @Column(nullable = false)
    private String bankBik;

}
