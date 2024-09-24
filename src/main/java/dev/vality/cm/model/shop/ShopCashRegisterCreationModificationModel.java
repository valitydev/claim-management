package dev.vality.cm.model.shop;

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
public class ShopCashRegisterCreationModificationModel extends ShopCashRegisterModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "cash_register_params_id", referencedColumnName = "id")
    private CashRegisterParamsModel cashRegisterParamsModel;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopCashRegisterCreationModificationModel
                && super.canEqual(that);
    }

}
