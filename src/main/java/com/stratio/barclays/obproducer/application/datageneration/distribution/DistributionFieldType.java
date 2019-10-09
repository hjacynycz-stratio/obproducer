package com.stratio.barclays.obproducer.application.datageneration.distribution;

public interface DistributionFieldType<T, R> {

  R distribute(T calculatedValue, boolean enableHistory);

}
