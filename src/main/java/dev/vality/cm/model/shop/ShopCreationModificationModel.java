package dev.vality.cm.model.shop;

import dev.vality.cm.model.ShopParamsModel;
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
public class ShopCreationModificationModel extends ShopModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "shop_params_id", referencedColumnName = "id")
    private ShopParamsModel shopParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopCreationModificationModel
                && super.canEqual(that);
    }

}
