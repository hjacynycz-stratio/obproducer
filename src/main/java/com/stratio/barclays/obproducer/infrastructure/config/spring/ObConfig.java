package com.stratio.barclays.obproducer.infrastructure.config.spring;

import javax.naming.OperationNotSupportedException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import com.stratio.barclays.obproducer.application.datageneration.calculation.FieldValueCalculator;
import com.stratio.barclays.obproducer.application.datageneration.calculation.FieldValueCalculatorFactory;
import com.stratio.barclays.obproducer.application.datageneration.distribution.DistributionFieldType;
import com.stratio.barclays.obproducer.application.datageneration.distribution.DistributionFieldTypeFactory;
import com.stratio.barclays.obproducer.application.datageneration.errors.IntroducedError;
import com.stratio.barclays.obproducer.application.datageneration.errors.IntroducedErrorFactory;
import com.stratio.barclays.obproducer.application.datageneration.reduction.FieldValueReductionStrategy;
import com.stratio.barclays.obproducer.application.datageneration.reduction.FieldValueReductionStrategyFactory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "ob")
@NoArgsConstructor
@Getter
@Setter
public class ObConfig implements InitializingBean {

  private int accountsSize;

  private int minPurchasesPerAccount;

  private int maxPurchasesPerAccount;

  private int minRemainingRecordsToGenerate;

  private String eventsGenerationCron;

  private String eventsEmissionCron;

  @NestedConfigurationProperty
  private List<ObErrorConfig> errors;

  private List<IntroducedError> introducedErrors;

  private String populationSelectField;

  @NestedConfigurationProperty
  private List<ObPopulationConfig> populations;

  private boolean enableHistory;

  @Override
  public void afterPropertiesSet() {
    getPopulations().forEach(obPopulationConfig -> obPopulationConfig.getFields().forEach(
        DistributionField::postConstruct));
    introducedErrors = getErrors().stream().map(obErrorConfig -> {
      try {
        return IntroducedErrorFactory
            .getInstance(obErrorConfig.getType(), obErrorConfig.getPercentage(), obErrorConfig.getMalformedField(),
                obErrorConfig.getParams());
      } catch (OperationNotSupportedException e) {
        throw new IllegalStateException("Bad configuration when setting errors");
      }
    })
        .collect(Collectors.toList());
  }

  @NoArgsConstructor
  @Getter
  @Setter
  public static class ObErrorConfig {

    private float percentage;

    private String malformedField;

    private String type;

    private List<String> params;
  }

  @NoArgsConstructor
  @Getter
  @Setter
  public static class ObPopulationConfig {

    private String name;

    private String regExp;

    private List<DistributionField> fields;

    public boolean matches(Object selectedFieldValue) {
      return selectedFieldValue.toString().matches(regExp);
    }
  }

  @NoArgsConstructor
  @Getter
  @Setter
  public static class DistributionField {

    private static final DistributionFieldTypeFactory distributionFieldTypeFactory =
        new DistributionFieldTypeFactory();
    private static final FieldValueCalculatorFactory fieldValueCalculatorFactory =
        new FieldValueCalculatorFactory();
    private static final FieldValueReductionStrategyFactory fieldValueReductionStrategyFactory =
        new FieldValueReductionStrategyFactory();

    @Getter
    private static final String algorithmDelimiter = ";";

    @Getter
    private static final String paramsDelimiter = " ";

    private String name;

    private String type;

    private List<String> distributionAlg;

    private String reductionStrategy;

    private DistributionFieldType distributionFieldType;

    private Map<String, FieldValueCalculator> fieldValueCalculators;

    private FieldValueReductionStrategy fieldValueReductionStrategy;

    public void postConstruct() {
      distributionFieldType = distributionFieldTypeFactory.buildInstance(type);
      fieldValueCalculators = distributionAlg
          .stream()
          .map(distributionAlg -> distributionAlg.split(algorithmDelimiter)[0])
          .distinct()
          .map(fieldValueCalculatorFactory::buildInstance)
          .collect(Collectors.toMap(FieldValueCalculator::getType, e -> e));
      fieldValueReductionStrategy = fieldValueReductionStrategyFactory.buildInstance(reductionStrategy);
    }

  }

}
