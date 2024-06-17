package dev.vality.cm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vality.geck.serializer.kit.json.JsonHandler;
import dev.vality.geck.serializer.kit.json.JsonProcessor;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.kit.tbase.TBaseProcessor;
import lombok.SneakyThrows;
import org.apache.thrift.TBase;

public class JsonUtil {

    @SneakyThrows
    public static String toJson(TBase object) {
        return object == null ? null :
                new TBaseProcessor().process(object, new JsonHandler()).toString();
    }

    @SneakyThrows
    public static <T extends TBase> T toTBase(String json, Class<T> clazz) {
        return json == null ? null :
                new JsonProcessor().process(new ObjectMapper().readTree(json), new TBaseHandler<>(clazz));
    }
}
