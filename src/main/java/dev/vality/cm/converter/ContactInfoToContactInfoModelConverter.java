package dev.vality.cm.converter;

import dev.vality.cm.model.ContactInfoModel;
import dev.vality.damsel.domain.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoToContactInfoModelConverter implements ClaimConverter<ContactInfo, ContactInfoModel> {
    @Override
    public ContactInfoModel convert(ContactInfo contactInfo) {
        ContactInfoModel contactInfoModel = new ContactInfoModel();
        contactInfoModel.setEmail(contactInfo.getEmail());
        contactInfoModel.setPhoneNumber(contactInfo.getPhoneNumber());
        return contactInfoModel;
    }
}
