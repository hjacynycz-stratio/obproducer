package com.stratio.barclays.obproducer.application.datageneration.calculation;

public class FieldValueCalculatorFactory {

  public FieldValueCalculator buildInstance(String distributionType) {

    switch (distributionType) {
      case "uniform":
        return new UniformFieldValueCalculator();
      case "gauss":
        return new GaussFieldValueCalculator();
      default:
        throw new UnsupportedOperationException(
            "Distribution type  not supported: " + distributionType);

    }
  }

}
