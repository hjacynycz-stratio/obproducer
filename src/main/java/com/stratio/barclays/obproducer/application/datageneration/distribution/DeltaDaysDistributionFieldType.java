package com.stratio.barclays.obproducer.application.datageneration.distribution;

import java.time.LocalDateTime;

public class DeltaDaysDistributionFieldType implements DistributionFieldType<Double, LocalDateTime> {

  @Override
  public LocalDateTime distribute(Double calculatedValue, boolean enableHistory) {

    return enableHistory ? LocalDateTime.now()
        .minusDays(Math.abs(calculatedValue.intValue()))
        : LocalDateTime.now().plusDays(Math.abs(calculatedValue.intValue()));
  }
}
