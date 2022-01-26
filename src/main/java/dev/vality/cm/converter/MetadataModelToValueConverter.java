package dev.vality.cm.converter;

import dev.vality.cm.model.MetadataModel;
import dev.vality.damsel.msgpack.Value;
import lombok.SneakyThrows;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.springframework.stereotype.Component;

@Component
public class MetadataModelToValueConverter implements ClaimConverter<MetadataModel, Value> {

    ThreadLocal<TDeserializer> thriftDeserializerThreadLocal =
            ThreadLocal.withInitial(() -> new TDeserializer(new TBinaryProtocol.Factory()));

    @Override
    @SneakyThrows
    public Value convert(MetadataModel metadataModel) {
        Value value = new Value();
        thriftDeserializerThreadLocal.get().deserialize(value, metadataModel.getValue());
        return value;
    }
}
