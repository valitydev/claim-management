package dev.vality.cm.model.shop;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TurnoverLimitsModificationModel extends ShopModificationModel {

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "limit", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TurnoverLimitModificationModel> limits;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof TurnoverLimitsModificationModel
                && super.canEqual(that);
    }

}
