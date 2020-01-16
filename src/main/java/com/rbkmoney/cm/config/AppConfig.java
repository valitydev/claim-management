package com.rbkmoney.cm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.cm.handler.ClaimHandlerEventSinkDecorator;
import com.rbkmoney.cm.handler.ClaimManagementHandler;
import com.rbkmoney.cm.repository.ClaimRepository;
import com.rbkmoney.cm.service.ClaimManagementService;
import com.rbkmoney.cm.service.ContinuationTokenService;
import com.rbkmoney.cm.util.ClaimEventFactory;
import com.rbkmoney.damsel.claim_management.ClaimManagementSrv;
import org.apache.thrift.TBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class AppConfig {

    @Bean
    public ContinuationTokenService continuationTokenService(@Value("${continuation-secret}") String secret, ObjectMapper objectMapper) {
        return new ContinuationTokenService(secret, objectMapper);
    }

    @Bean
    public ClaimManagementService claimManagementService(ClaimRepository claimRepository, ContinuationTokenService continuationTokenService) {
        return new ClaimManagementService(claimRepository, continuationTokenService);
    }

    @Bean
    public ClaimManagementSrv.Iface claimManagementHandler(
            ClaimManagementService claimManagementService,
            ConversionService conversionService,
            KafkaTemplate<String, TBase> kafkaTemplate,
            ClaimEventFactory claimEventFactory,
            RetryTemplate retryTemplate
    ) {
        return new ClaimHandlerEventSinkDecorator(
                new ClaimManagementHandler(claimManagementService, conversionService),
                kafkaTemplate,
                claimEventFactory,
                retryTemplate);
    }
}
