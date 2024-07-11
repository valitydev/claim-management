package dev.vality.cm.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Data
@Entity
public class ContactInfoModel {

    @Id
    @GeneratedValue
    private long id;

    private String phoneNumber;

    private String email;

    private String  firstName;

    private String lastName;

    private String country;

    private String state;

    private String city;

    private String address;

    private String postalCode;

}
