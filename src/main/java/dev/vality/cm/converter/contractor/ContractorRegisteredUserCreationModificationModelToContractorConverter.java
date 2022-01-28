package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorRegisteredUserCreationModificationModel;
import dev.vality.damsel.domain.Contractor;
import dev.vality.damsel.domain.RegisteredUser;
import org.springframework.stereotype.Component;

@Component
public class ContractorRegisteredUserCreationModificationModelToContractorConverter
        implements ClaimConverter<ContractorRegisteredUserCreationModificationModel, Contractor> {
    @Override
    public Contractor convert(
            ContractorRegisteredUserCreationModificationModel contractorRegisteredUserCreationModificationModel) {
        return Contractor.registered_user(
                new RegisteredUser()
                        .setEmail(contractorRegisteredUserCreationModificationModel.getEmail())
        );
    }
}
