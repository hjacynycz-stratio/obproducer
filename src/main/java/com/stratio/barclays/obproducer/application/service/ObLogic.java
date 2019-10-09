package com.stratio.barclays.obproducer.application.service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stratio.barclays.obproducer.application.repository.ObTransactionDataRepository;
import com.stratio.barclays.obproducer.domain.ObTransactionData;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class ObLogic {

  private final EventProducer<String> eventProducer;

  private final ObTransactionDataRepository obTransactionDataRepository;

  private final ObjectMapper objectMapper;

  private final ObGenerationEngine obGenerationEngine;

  private final int minRemainingRecordsToGenerate;

  @Scheduled(cron = "#{obConfig.getEventsGenerationCron()}")
  @Transactional
  public void generateTransactionData() {

    log.info("About to prepare a batch of OpenBanking data...");

    var remainingRecords = obTransactionDataRepository
        .countBySentFalse();

    if (remainingRecords < minRemainingRecordsToGenerate) {

      var generatedRecords = (List<ObTransactionData>) obTransactionDataRepository.saveAll(obGenerationEngine.run());
      log.info(generatedRecords.size() + " OpenBanking data records generated and stored in DB");

    } else {
      log.info(remainingRecords
          + " OpenBanking data records still available to be sent. No more records must be generated for now");
    }

  }

  @Scheduled(cron = "#{obConfig.getEventsEmissionCron()}")
  @Transactional
  public void sendTransactionEvents() {

    log.info("About to send OpenBanking data to Kafka...");

    var someTransactionsUpToToday = (List<ObTransactionData>) obTransactionDataRepository
        .findTop100ByTransactionBookingDateTimeLessThanEqualAndSentFalse(LocalDateTime.now());

    log.info("Retrieved " + someTransactionsUpToToday.size() + " transactions for today which are not sent yet");

    someTransactionsUpToToday.forEach(obTransactionData -> {
      try {
        eventProducer.send(objectMapper.writeValueAsString(obTransactionData));
        obTransactionData.setSent(true);
      } catch (Exception e) {
        throw new IllegalStateException(
            "Something went wrong sending the event corresponding to the transaction " + obTransactionData
                + " to Kafka");
      }
    });

    var sentEventsSize = ((List<ObTransactionData>) obTransactionDataRepository.saveAll(someTransactionsUpToToday))
        .size();

    log.info(sentEventsSize + " records properly sent to Kafka. Marked as SENT in DB");
  }

}
