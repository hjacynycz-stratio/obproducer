package com.stratio.barclays.obproducer.application.datageneration.calculation;

public interface FieldValueCalculator {

  Double calculate(String... params);

  String getType();
}
