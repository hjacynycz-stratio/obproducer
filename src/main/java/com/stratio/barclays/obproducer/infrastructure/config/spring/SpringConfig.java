package com.stratio.barclays.obproducer.infrastructure.config.spring;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stratio.barclays.obproducer.application.repository.ObTransactionDataRepository;
import com.stratio.barclays.obproducer.application.service.EventProducer;
import com.stratio.barclays.obproducer.application.service.ObGenerationEngine;
import com.stratio.barclays.obproducer.application.service.ObLogic;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SpringConfig {

  private final EventProducer eventProducer;

  private final ObTransactionDataRepository obTransactionDataRepository;

  private final ObConfig obConfig;

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public ObLogic obLogic(ObjectMapper objectMapper, ObGenerationEngine obGenerationEngine) throws IOException {
    return new ObLogic(eventProducer, obTransactionDataRepository, objectMapper, obGenerationEngine,
        obConfig.getMinRemainingRecordsToGenerate());
  }

  @Bean
  public ObGenerationEngine obGenerationEngine() {
    return new ObGenerationEngine(obConfig);
  }
}
