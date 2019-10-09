package com.stratio.barclays.obproducer.application.datageneration.calculation;

import java.util.Random;

public class UniformFieldValueCalculator implements FieldValueCalculator {

  @Override
  public Double calculate(String... params) {
    return new Random()
        .doubles(Integer.parseInt(params[0]), Integer.parseInt(params[1])).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Error calculating using uniform distribution"));
  }

  @Override
  public String getType() {
    return "uniform";
  }
}
