package dev.vality.cm.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
