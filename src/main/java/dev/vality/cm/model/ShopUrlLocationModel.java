package dev.vality.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@DiscriminatorValue("url")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ShopUrlLocationModel extends ShopLocationModel {

    @NotNull
    private String url;

}
