package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.PrivateEntityModel;
import dev.vality.cm.model.contractor.ContractorPrivateEntityCreationModificationModel;
import dev.vality.damsel.domain.PrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class PrivateEntityToContractorPrivateEntityCreationModificationModelConverter
        implements ClaimConverter<PrivateEntity, ContractorPrivateEntityCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorPrivateEntityCreationModificationModel convert(PrivateEntity privateEntity) {
        ContractorPrivateEntityCreationModificationModel contractorPrivateEntityCreationModificationModel =
                new ContractorPrivateEntityCreationModificationModel();
        contractorPrivateEntityCreationModificationModel
                .setPrivateEntity(conversionService.convert(privateEntity, PrivateEntityModel.class));
        return contractorPrivateEntityCreationModificationModel;
    }
}
