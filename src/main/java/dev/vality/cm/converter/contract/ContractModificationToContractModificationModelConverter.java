package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.*;
import dev.vality.damsel.claim_management.ContractModification;
import dev.vality.cm.model.contract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractModificationToContractModificationModelConverter
        implements ClaimConverter<ContractModification, ContractModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModificationModel convert(ContractModification contractModification) {
        switch (contractModification.getSetField()) {
            case CREATION:
                return conversionService
                        .convert(contractModification.getCreation(), ContractCreationModificationModel.class);
            case TERMINATION:
                return conversionService
                        .convert(contractModification.getTermination(), ContractTerminationModificationModel.class);
            case PAYOUT_TOOL_MODIFICATION:
                return conversionService.convert(contractModification.getPayoutToolModification(),
                        ContractPayoutToolModificationModel.class);
            case ADJUSTMENT_MODIFICATION:
                return conversionService.convert(contractModification.getAdjustmentModification(),
                        ContractAdjustmentModificationModel.class);
            case CONTRACTOR_MODIFICATION:
                return conversionService.convert(contractModification.getContractorModification(),
                        ContractContractorChangeModificationModel.class);
            case LEGAL_AGREEMENT_BINDING:
                return conversionService.convert(contractModification.getLegalAgreementBinding(),
                        ContractLegalAgreementBindingModificationModel.class);
            case REPORT_PREFERENCES_MODIFICATION:
                return conversionService.convert(contractModification.getReportPreferencesModification(),
                        ContractReportPreferencesModificationModel.class);
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", contractModification.getSetField()));
        }
    }
}
