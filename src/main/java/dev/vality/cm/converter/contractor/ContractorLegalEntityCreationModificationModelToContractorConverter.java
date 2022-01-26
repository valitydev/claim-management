package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorLegalEntityCreationModificationModel;
import dev.vality.damsel.domain.Contractor;
import dev.vality.damsel.domain.LegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorLegalEntityCreationModificationModelToContractorConverter
        implements ClaimConverter<ContractorLegalEntityCreationModificationModel, Contractor> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Contractor convert(
            ContractorLegalEntityCreationModificationModel contractorLegalEntityCreationModificationModel) {
        return Contractor.legal_entity(
                conversionService
                        .convert(contractorLegalEntityCreationModificationModel.getLegalEntity(), LegalEntity.class)
        );
    }
}
