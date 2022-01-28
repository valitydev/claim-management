package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.LegalEntityModel;
import dev.vality.cm.model.contractor.ContractorLegalEntityCreationModificationModel;
import dev.vality.damsel.domain.LegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class LegalEntityToContractorLegalEntityCreationModificationModelConverter
        implements ClaimConverter<LegalEntity, ContractorLegalEntityCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorLegalEntityCreationModificationModel convert(LegalEntity legalEntity) {
        ContractorLegalEntityCreationModificationModel contractorLegalEntityCreationModificationModel =
                new ContractorLegalEntityCreationModificationModel();
        contractorLegalEntityCreationModificationModel
                .setLegalEntity(conversionService.convert(legalEntity, LegalEntityModel.class));
        return contractorLegalEntityCreationModificationModel;
    }
}
