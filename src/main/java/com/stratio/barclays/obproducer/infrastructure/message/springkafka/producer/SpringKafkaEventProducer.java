package com.stratio.barclays.obproducer.infrastructure.message.springkafka.producer;

import org.springframework.kafka.core.KafkaTemplate;

import com.stratio.barclays.obproducer.application.service.EventProducer;
import com.stratio.barclays.obproducer.infrastructure.message.springkafka.producer.config.SpringKafkaProducerTopicConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class SpringKafkaEventProducer implements EventProducer<String> {

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final SpringKafkaProducerTopicConfig topicToProduceTo;

  public void send(String eventToProduce) {

    log.debug("Producing event {} to topic {}...", eventToProduce, topicToProduceTo.getName());
    kafkaTemplate.send(topicToProduceTo.getName(), eventToProduce);
  }

}
