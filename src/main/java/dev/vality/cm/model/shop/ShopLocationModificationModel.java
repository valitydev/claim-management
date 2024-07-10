package dev.vality.cm.model.shop;

import dev.vality.cm.model.ShopLocationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ShopLocationModificationModel extends ShopModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "shop_location_id", referencedColumnName = "id")
    private ShopLocationModel location;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopLocationModificationModel
                && super.canEqual(that);
    }

}
