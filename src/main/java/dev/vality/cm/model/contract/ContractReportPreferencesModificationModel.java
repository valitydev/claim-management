package dev.vality.cm.model.contract;

import dev.vality.cm.model.ServiceAcceptanceActPreferencesModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ContractReportPreferencesModificationModel extends ContractModificationModel {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_acceptance_act_preferences_id", referencedColumnName = "id")
    private ServiceAcceptanceActPreferencesModel serviceAcceptanceActPreferences;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ContractReportPreferencesModificationModel
                && super.canEqual(that);
    }

}
