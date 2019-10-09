package com.stratio.barclays.obproducer.application.datageneration.reduction;

public interface FieldValueReductionStrategy {

  Double reduce(Double previous, Double current);

}
