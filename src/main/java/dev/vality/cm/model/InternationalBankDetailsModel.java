package dev.vality.cm.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Data
@Entity
public class InternationalBankDetailsModel {

    @Id
    @GeneratedValue
    private long id;

    private String bic;

    private Integer countryCode;

    private String name;

    private String address;

    private String abaRtn;

}
