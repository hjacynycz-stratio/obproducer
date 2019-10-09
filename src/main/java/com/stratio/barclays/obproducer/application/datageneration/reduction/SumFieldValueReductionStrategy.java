package com.stratio.barclays.obproducer.application.datageneration.reduction;

public class SumFieldValueReductionStrategy implements FieldValueReductionStrategy {

  @Override
  public Double reduce(Double previous, Double current) {
    return previous + current;
  }
}
