package com.stratio.barclays.obproducer.application.datageneration.distribution;

public class DistributionFieldTypeFactory {

  public DistributionFieldType buildInstance(String type) {
    switch (type) {
      case "bigDecimal":
        return new BigDecimalDistributionFieldType();
      case "deltaDays":
        return new DeltaDaysDistributionFieldType();
      default:
        throw new UnsupportedOperationException(
            "Data type for distribution not supported: " + type);
    }
  }

}
