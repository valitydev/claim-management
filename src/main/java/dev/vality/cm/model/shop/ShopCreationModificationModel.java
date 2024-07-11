package dev.vality.cm.model.shop;

import dev.vality.cm.model.ShopParamsModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

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
