package com.stratio.barclays.obproducer.application.datageneration.distribution;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalDistributionFieldType implements DistributionFieldType<BigDecimal> {

  @Override
  public BigDecimal distribute(Double calculatedValue, boolean enableHistory) {
    return new BigDecimal(Math.abs(calculatedValue)).setScale(5, RoundingMode.CEILING);
  }
}
