package com.stratio.barclays.obproducer.application.datageneration.distribution;

public interface DistributionFieldType<T> {

  T distribute(Double calculatedValue, boolean enableHistory);

}
