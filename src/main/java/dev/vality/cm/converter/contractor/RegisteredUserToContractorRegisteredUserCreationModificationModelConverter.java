package dev.vality.cm.converter.contractor;

import dev.vality.cm.converter.ClaimConverter;
import dev.vality.cm.model.contractor.ContractorRegisteredUserCreationModificationModel;
import dev.vality.damsel.domain.RegisteredUser;
import org.springframework.stereotype.Component;

@Component
public class RegisteredUserToContractorRegisteredUserCreationModificationModelConverter
        implements ClaimConverter<RegisteredUser, ContractorRegisteredUserCreationModificationModel> {
    @Override
    public ContractorRegisteredUserCreationModificationModel convert(RegisteredUser registeredUser) {
        ContractorRegisteredUserCreationModificationModel contractorRegisteredUserCreationModificationModel =
                new ContractorRegisteredUserCreationModificationModel();
        contractorRegisteredUserCreationModificationModel.setEmail(registeredUser.getEmail());
        return contractorRegisteredUserCreationModificationModel;
    }
}
