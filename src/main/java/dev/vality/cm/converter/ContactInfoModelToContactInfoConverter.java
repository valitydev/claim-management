package dev.vality.cm.converter;

import dev.vality.cm.model.ContactInfoModel;
import dev.vality.damsel.domain.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoModelToContactInfoConverter implements ClaimConverter<ContactInfoModel, ContactInfo> {
    @Override
    public ContactInfo convert(ContactInfoModel contactInfoModel) {
        return new ContactInfo()
                .setEmail(contactInfoModel.getEmail())
                .setPhoneNumber(contactInfoModel.getPhoneNumber());
    }
}
