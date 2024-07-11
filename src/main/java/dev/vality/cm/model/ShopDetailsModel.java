package dev.vality.cm.model;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
public class ShopDetailsModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    private String description;

}
