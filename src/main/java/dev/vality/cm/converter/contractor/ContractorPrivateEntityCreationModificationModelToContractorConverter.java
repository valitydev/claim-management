package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorPrivateEntityCreationModificationModel;
import dev.vality.damsel.domain.Contractor;
import dev.vality.damsel.domain.PrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorPrivateEntityCreationModificationModelToContractorConverter
        implements ClaimConverter<ContractorPrivateEntityCreationModificationModel, Contractor> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Contractor convert(
            ContractorPrivateEntityCreationModificationModel contractorPrivateEntityCreationModificationModel) {
        return Contractor.private_entity(
                conversionService.convert(
                        contractorPrivateEntityCreationModificationModel.getPrivateEntity(),
                        PrivateEntity.class
                )
        );
    }
}
