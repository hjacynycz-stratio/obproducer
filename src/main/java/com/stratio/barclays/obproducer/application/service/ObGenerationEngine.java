package com.stratio.barclays.obproducer.application.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.stratio.barclays.obproducer.domain.ObTransactionData;
import com.stratio.barclays.obproducer.infrastructure.config.spring.ObConfig;
import com.stratio.barclays.obproducer.infrastructure.config.spring.ObConfig.DistributionField;

import lombok.RequiredArgsConstructor;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RequiredArgsConstructor
public class ObGenerationEngine {

  private static final PodamFactory pojoGenerator = new PodamFactoryImpl();

  private final ObConfig obConfig;

  private ObTransactionData addErrors(ObTransactionData obTransactionData,
      Random randomPercentageGenerator) {

    var randomPercentage = randomPercentageGenerator.nextInt(100);

    obConfig.getIntroducedErrors().forEach(obErrorConfig -> {

      if (randomPercentage < obErrorConfig.getPercentage()) {
        try {
          obErrorConfig.introduceError(obTransactionData);
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
          throw new IllegalStateException("Something went wrong when introducing errors: " + e.getMessage());
        }
      }
    });

    return obTransactionData;
  }

  private ObTransactionData selectPopulation(ObTransactionData obTransactionData) {

    try {

      var selectFieldValue = new PropertyDescriptor(obConfig.getPopulationSelectField(), ObTransactionData.class)
          .getReadMethod().invoke(obTransactionData);

      var obPopulation = obConfig.getPopulations()
          .stream()
          .filter(obPopulationConfig -> obPopulationConfig.matches(selectFieldValue))
          .findFirst().orElseThrow(() -> new IllegalArgumentException(
              "Something went wrong when selecting population: No population defined matches the regular expression"));

      obTransactionData.setObPopulation(obPopulation);

      return obTransactionData;

    } catch (Exception e) {
      throw new IllegalArgumentException("Something went wrong when selecting population: " + e.getMessage());
    }
  }

  private ObTransactionData populate(ObTransactionData obTransactionData) {

    obTransactionData.getObPopulationConfig().getFields()
        .forEach(distributionField -> {
          try {
            var clazz = ObTransactionData.class.getDeclaredField(distributionField.getName()).getType();
            new PropertyDescriptor(distributionField.getName(), ObTransactionData.class)
                .getWriteMethod()
                .invoke(obTransactionData, clazz.cast(calculateFieldValue(distributionField)));
          } catch (Exception e) {
            throw new IllegalArgumentException("Something went wrong when populating data: " + e.getMessage());
          }
        });

    return obTransactionData;
  }

  //CHECKSTYLE:OFF
  private <T extends Object> T calculateFieldValue(DistributionField distributionField) {

    var fieldValueReductionStrategy = distributionField.getFieldValueReductionStrategy();

    //The reduction phase has the constraint to use doubles to calculate value.
    // Casting phase to the desired type must be implemented in the distribution phase
    var calculatedValue = distributionField
        .getDistributionAlg()
        .stream()
        .reduce(0d, (Double current, String algParams) -> {
          var alg = algParams.split(DistributionField.getAlgorithmDelimiter())[0];
          var params = algParams.split(DistributionField.getAlgorithmDelimiter())[1];
          return fieldValueReductionStrategy
              .reduce(current,
                  distributionField.getFieldValueCalculators().get(alg).calculate(params.split(
                      DistributionField.getParamsDelimiter())));
        }, fieldValueReductionStrategy::reduce);

    return (T) distributionField.getDistributionFieldType()
        .distribute(calculatedValue, obConfig.isEnableHistory());
  }
  //CHECKSTYLE.ON

  private List<ObTransactionData> generateObTransactionsPerAccountId(int accountId) {

    return Stream.generate(() -> {

      var transactionData = pojoGenerator.manufacturePojo(ObTransactionData.class);
      transactionData.setAccountId(String.valueOf(accountId));
      transactionData.setTransactionCardPanNumber(String.valueOf(accountId));
      return transactionData;

    }).limit(ThreadLocalRandom.current()
        .nextInt(obConfig.getMinPurchasesPerAccount(), obConfig.getMaxPurchasesPerAccount() + 1))
        .collect(Collectors.toList());
  }

  List<ObTransactionData> run() {

    var randomPercentageGenerator = new Random();

    var generatedRecords = IntStream.range(0, obConfig.getAccountsSize())
        .parallel()
        .mapToObj(this::generateObTransactionsPerAccountId)
        .flatMap(Collection::stream)
        .map(this::selectPopulation)
        .map(this::populate)
        .map(obTransactionData -> addErrors(obTransactionData, randomPercentageGenerator))
        .collect(Collectors.toList());

    obConfig.setEnableHistory(false);

    return generatedRecords;

  }
}
