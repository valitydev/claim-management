package dev.vality.cm.converter;

import dev.vality.cm.model.ContractParamsModel;
import dev.vality.damsel.claim_management.ContractParams;
import dev.vality.damsel.domain.ContractTemplateRef;
import dev.vality.damsel.domain.PaymentInstitutionRef;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContractParamsModelToContractParamsConverter
        implements ClaimConverter<ContractParamsModel, ContractParams> {
    @Override
    public ContractParams convert(ContractParamsModel contractParamsModel) {
        return new ContractParams()
                .setContractorId(contractParamsModel.getContractorId())
                .setTemplate(
                        Optional.ofNullable(contractParamsModel.getContractTemplateId())
                                .map(ContractTemplateRef::new)
                                .orElse(null)
                )
                .setPaymentInstitution(
                        Optional.ofNullable(contractParamsModel.getPaymentInstitutionId())
                                .map(PaymentInstitutionRef::new)
                                .orElse(null)
                );
    }
}
