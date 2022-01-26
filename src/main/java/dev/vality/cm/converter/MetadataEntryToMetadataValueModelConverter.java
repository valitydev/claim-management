package dev.vality.cm.converter;

import dev.vality.cm.model.MetadataModel;
import dev.vality.damsel.msgpack.Value;
import lombok.SneakyThrows;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MetadataEntryToMetadataValueModelConverter
        implements ClaimConverter<Map.Entry<String, Value>, MetadataModel> {

    ThreadLocal<TSerializer> thriftSerializerThreadLocal =
            ThreadLocal.withInitial(() -> {
                try {
                    return new TSerializer(new TBinaryProtocol.Factory());
                } catch (TTransportException e) {
                    throw new RuntimeException();
                }
            });

    @Override
    @SneakyThrows
    public MetadataModel convert(Map.Entry<String, Value> metadataEntry) {
        MetadataModel metadataModel = new MetadataModel();
        metadataModel.setKey(metadataEntry.getKey());
        metadataModel.setValue(thriftSerializerThreadLocal.get().serialize(metadataEntry.getValue()));
        return metadataModel;
    }
}
