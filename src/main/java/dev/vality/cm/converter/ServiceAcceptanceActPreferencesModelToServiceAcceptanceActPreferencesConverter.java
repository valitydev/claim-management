package dev.vality.cm.converter;

import dev.vality.cm.model.ServiceAcceptanceActPreferencesModel;
import dev.vality.damsel.domain.BusinessScheduleRef;
import dev.vality.damsel.domain.Representative;
import dev.vality.damsel.domain.ServiceAcceptanceActPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ServiceAcceptanceActPreferencesModelToServiceAcceptanceActPreferencesConverter
        implements ClaimConverter<ServiceAcceptanceActPreferencesModel, ServiceAcceptanceActPreferences> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ServiceAcceptanceActPreferences convert(
            ServiceAcceptanceActPreferencesModel serviceAcceptanceActPreferencesModel) {
        return new ServiceAcceptanceActPreferences()
                .setSchedule(new BusinessScheduleRef(serviceAcceptanceActPreferencesModel.getSchedulerId()))
                .setSigner(conversionService
                        .convert(serviceAcceptanceActPreferencesModel.getSigner(), Representative.class));
    }
}
