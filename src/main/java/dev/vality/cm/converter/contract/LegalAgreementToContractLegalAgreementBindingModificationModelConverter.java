package dev.vality.cm.converter.contract;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.LegalAgreementModel;
import dev.vality.cm.model.contract.ContractLegalAgreementBindingModificationModel;
import dev.vality.damsel.domain.LegalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class LegalAgreementToContractLegalAgreementBindingModificationModelConverter
        implements ClaimConverter<LegalAgreement, ContractLegalAgreementBindingModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractLegalAgreementBindingModificationModel convert(LegalAgreement legalAgreement) {
        ContractLegalAgreementBindingModificationModel contractLegalAgreementBindingModificationModel =
                new ContractLegalAgreementBindingModificationModel();
        contractLegalAgreementBindingModificationModel
                .setLegalAgreement(conversionService.convert(legalAgreement, LegalAgreementModel.class));
        return contractLegalAgreementBindingModificationModel;
    }
}
