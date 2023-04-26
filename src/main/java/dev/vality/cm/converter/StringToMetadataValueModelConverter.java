package dev.vality.cm.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vality.cm.model.MetadataModel;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StringToMetadataValueModelConverter
        implements ClaimConverter<String, List<MetadataModel>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public List<MetadataModel> convert(String stringMetadata) {
        return objectMapper.readValue(stringMetadata, new TypeReference<>() {
        });
    }
}
