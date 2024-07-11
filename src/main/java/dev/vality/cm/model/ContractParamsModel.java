package dev.vality.cm.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Data
@Entity
public class ContractParamsModel {

    @Id
    @GeneratedValue
    private long id;

    private String contractorId;

    private Integer contractTemplateId;

    private Integer paymentInstitutionId;

}
