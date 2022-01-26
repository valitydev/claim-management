package dev.vality.cm.config;

import dev.vality.cm.handler.event.ClaimEventSinkManager;
import dev.vality.cm.listener.ClaimEventSinkListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConsumerBeanEnableConfig {

    @Bean
    @ConditionalOnProperty(value = "kafka.topics.claim-event-sink.enabled", havingValue = "true")
    public ClaimEventSinkListener paymentEventsKafkaListener(ClaimEventSinkManager claimEventSinkManager) {
        return new ClaimEventSinkListener(claimEventSinkManager);
    }

}
