package dev.vality.cm.config;

import dev.vality.cm.serde.ClaimManagementEventDeserializer;
import dev.vality.damsel.claim_management.Event;
import dev.vality.kafka.common.exception.handler.ExponentialBackOffDefaultErrorHandler;
import dev.vality.kafka.common.serialization.ThriftSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.thrift.TBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;
    @Value("${kafka.consumer.concurrency}")
    private int concurrency;
    @Value("${kafka.error-handler.min-sleep-time-seconds}")
    private int errorHandlerMinSleepTimeSeconds;
    @Value("${kafka.error-handler.max-sleep-time-seconds}")
    private int errorHandlerMaxSleepTimeSeconds;

    @Bean
    public ProducerFactory<String, TBase> producerFactory() {
        Map<String, Object> configProps = kafkaProperties.buildProducerProperties();
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ThriftSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<String, Event> consumerFactory() {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ClaimManagementEventDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    @SuppressWarnings("LineLength")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Event>> kafkaListenerContainerFactory(
            ConsumerFactory<String, Event> consumerFactory,
            DefaultErrorHandler errorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setCommonErrorHandler(errorHandler);
        factory.setConcurrency(concurrency);
        return factory;
    }

    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {
        ExponentialBackOff backOff = new ExponentialBackOff();
        backOff.setInitialInterval(errorHandlerMinSleepTimeSeconds);
        backOff.setMaxInterval(errorHandlerMaxSleepTimeSeconds);
        ExponentialBackOffDefaultErrorHandler errorHandler =
                new ExponentialBackOffDefaultErrorHandler(new ExponentialBackOff());
        errorHandler.setAckAfterHandle(false);
        return errorHandler;
    }

    @Bean
    public KafkaTemplate<String, TBase> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
