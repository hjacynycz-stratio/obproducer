package com.stratio.barclays.obproducer.application.datageneration.reduction;

public class FieldValueReductionStrategyFactory {

  public FieldValueReductionStrategy buildInstance(String reductionStrategy) {

    switch (reductionStrategy) {
      case "sum":
        return new SumFieldValueReductionStrategy();
      default:
        throw new UnsupportedOperationException("Reduction strategy not implemented yet or not specified");
    }
  }

}
