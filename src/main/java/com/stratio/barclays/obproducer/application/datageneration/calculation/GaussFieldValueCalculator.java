package com.stratio.barclays.obproducer.application.datageneration.calculation;

import java.util.Random;

public class GaussFieldValueCalculator implements FieldValueCalculator {

  @Override
  public Double calculate(String... params) {
    return new Random().nextGaussian() * Integer.parseInt(params[0]) + Integer
        .parseInt(params[1]);
  }

  @Override
  public String getType() {
    return "gauss";
  }
}
