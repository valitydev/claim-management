package dev.vality.cm.converter;

import dev.vality.cm.model.RepresentativeModel;
import dev.vality.cm.model.ServiceAcceptanceActPreferencesModel;
import dev.vality.damsel.domain.ServiceAcceptanceActPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ServiceAcceptanceActPreferencesToServiceAcceptanceActPreferencesModelConverter
        implements ClaimConverter<ServiceAcceptanceActPreferences, ServiceAcceptanceActPreferencesModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ServiceAcceptanceActPreferencesModel convert(
            ServiceAcceptanceActPreferences serviceAcceptanceActPreferences) {
        ServiceAcceptanceActPreferencesModel serviceAcceptanceActPreferencesModel =
                new ServiceAcceptanceActPreferencesModel();
        serviceAcceptanceActPreferencesModel.setSchedulerId(serviceAcceptanceActPreferences.getSchedule().getId());
        serviceAcceptanceActPreferencesModel.setSigner(
                conversionService.convert(serviceAcceptanceActPreferences.getSigner(), RepresentativeModel.class));
        return serviceAcceptanceActPreferencesModel;
    }
}
