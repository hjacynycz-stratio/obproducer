package com.stratio.barclays.obproducer.infrastructure.message.springkafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.stratio.barclays.obproducer.application.service.EventProducer;
import com.stratio.barclays.obproducer.infrastructure.message.springkafka.producer.SpringKafkaEventProducer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SpringKafkaProducerConfig {

  private final SpringKafkaProducerTopicConfig topicToProduceTo;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Bean
  public NewTopic producerTopic() {
    return new NewTopic(topicToProduceTo.getName(), topicToProduceTo.getPartitions(), topicToProduceTo.getReplicas());
  }

  @Bean
  public EventProducer<String> springKafkaEventProducer() {
    return new SpringKafkaEventProducer(kafkaTemplate, topicToProduceTo);
  }

}
