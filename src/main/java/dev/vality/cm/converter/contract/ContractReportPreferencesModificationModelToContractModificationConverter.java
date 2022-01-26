package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contract.ContractReportPreferencesModificationModel;
import dev.vality.damsel.claim_management.ContractModification;
import dev.vality.damsel.domain.ReportPreferences;
import dev.vality.damsel.domain.ServiceAcceptanceActPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContractReportPreferencesModificationModelToContractModificationConverter
        implements ClaimConverter<ContractReportPreferencesModificationModel, ContractModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModification convert(
            ContractReportPreferencesModificationModel contractReportPreferencesModificationModel) {
        return ContractModification.report_preferences_modification(
                new ReportPreferences()
                        .setServiceAcceptanceActPreferences(
                                Optional.ofNullable(
                                        contractReportPreferencesModificationModel.getServiceAcceptanceActPreferences())
                                        .map(serviceAcceptanceActPreferencesModel -> conversionService.convert(
                                                serviceAcceptanceActPreferencesModel,
                                                ServiceAcceptanceActPreferences.class
                                                )
                                        ).orElse(null)
                        )
        );
    }
}
