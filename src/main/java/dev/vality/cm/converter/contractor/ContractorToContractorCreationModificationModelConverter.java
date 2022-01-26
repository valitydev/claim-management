package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorCreationModificationModel;
import dev.vality.cm.model.contractor.ContractorLegalEntityCreationModificationModel;
import dev.vality.cm.model.contractor.ContractorPrivateEntityCreationModificationModel;
import dev.vality.cm.model.contractor.ContractorRegisteredUserCreationModificationModel;
import dev.vality.damsel.domain.Contractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ContractorToContractorCreationModificationModelConverter
        implements ClaimConverter<Contractor, ContractorCreationModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractorCreationModificationModel convert(Contractor contractor) {
        switch (contractor.getSetField()) {
            case LEGAL_ENTITY:
                return conversionService
                        .convert(contractor.getLegalEntity(), ContractorLegalEntityCreationModificationModel.class);
            case PRIVATE_ENTITY:
                return conversionService
                        .convert(contractor.getPrivateEntity(), ContractorPrivateEntityCreationModificationModel.class);
            case REGISTERED_USER:
                return conversionService.convert(contractor.getRegisteredUser(),
                        ContractorRegisteredUserCreationModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", contractor.getSetField()));
        }
    }
}
