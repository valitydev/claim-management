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
                .setPhoneNumber(contactInfoModel.getPhoneNumber())
                .setFirstName(contactInfoModel.getFirstName())
                .setLastName(contactInfoModel.getLastName())
                .setAddress(contactInfoModel.getAddress())
                .setCountry(contactInfoModel.getCountry())
                .setCity(contactInfoModel.getCity())
                .setPostalCode(contactInfoModel.getPostalCode())
                .setState(contactInfoModel.getState());
    }
}
