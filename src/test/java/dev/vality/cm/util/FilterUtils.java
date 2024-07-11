package dev.vality.cm.util;

import dev.vality.damsel.claim_management.*;

public class FilterUtils { // TODO remove after add new modifications

    public static boolean isUnusedModification(ShopModification shopModification) {
        return shopModification.isSetTurnoverLimitsModification();
    }

    public static boolean isUnusedModification(ContractorModification contractorModification) {
        return contractorModification.isSetCreation()
                && contractorModification.getCreation().isSetDummyEntity();
    }

    public static boolean isUnusedModification(ContractModification contractModification) {
        return contractModification.isSetPayoutToolModification()
                && contractModification.getPayoutToolModification().isSetModification()
                && contractModification.getPayoutToolModification().getModification().isSetCreation()
                && contractModification.getPayoutToolModification().getModification().getCreation().isSetToolInfo()
                && contractModification.getPayoutToolModification().getModification().getCreation().getToolInfo()
                .isSetDummyAccount();
    }

    public static boolean isUnusedModification(ModificationUnit modificationUnit) {
        return isUnusedModification(modificationUnit.getModification());
    }

    public static boolean isUnusedModification(Modification modification) {
        return modification.isSetPartyModification() && isUnusedModification(modification.getPartyModification());
    }

    public static boolean isUnusedModification(PartyModification partyModification) {
        return (
                partyModification.isSetShopModification()
                        && isUnusedModification(partyModification.getShopModification().getModification()))
                ||
                (partyModification.isSetContractorModification()
                        && isUnusedModification(partyModification.getContractorModification().getModification()))
                ||
                (partyModification.isSetContractModification()
                        && isUnusedModification(partyModification.getContractModification().getModification()));
    }
}
