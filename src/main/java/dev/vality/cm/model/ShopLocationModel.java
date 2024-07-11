package dev.vality.cm.model;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ShopLocationModel {

    @Id
    @GeneratedValue
    private long id;

}
