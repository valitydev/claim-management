package dev.vality.cm.model.shop;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ShopContractModificationModel extends ShopModificationModel {

    @NotNull
    @Column(nullable = false)
    private String contractId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopContractModificationModel
                && super.canEqual(that);
    }

}
