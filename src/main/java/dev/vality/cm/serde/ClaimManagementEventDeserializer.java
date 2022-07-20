package dev.vality.cm.serde;

import dev.vality.damsel.claim_management.Event;
import dev.vality.kafka.common.serialization.AbstractThriftDeserializer;

public class ClaimManagementEventDeserializer extends AbstractThriftDeserializer<Event> {

    @Override
    public Event deserialize(String topic, byte[] data) {
        return this.deserialize(data, new Event());
    }

}
