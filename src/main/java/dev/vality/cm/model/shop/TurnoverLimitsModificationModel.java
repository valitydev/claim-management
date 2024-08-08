package dev.vality.cm.model.shop;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TurnoverLimitsModificationModel extends ShopModificationModel {

    @OneToMany(mappedBy = "limit", fetch = FetchType.EAGER)
    private Set<TurnoverLimitModificationModel> limits;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopLocationModificationModel
                && super.canEqual(that);
    }

}
