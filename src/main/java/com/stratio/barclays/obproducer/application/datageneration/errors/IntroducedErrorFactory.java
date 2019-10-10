package com.stratio.barclays.obproducer.application.datageneration.errors;

import javax.naming.OperationNotSupportedException;

import java.util.List;

public class IntroducedErrorFactory<T> {

  public IntroducedError getInstance(String type, float percentage, String malformedField, List<String> params)
      throws OperationNotSupportedException {

    switch (type) {
      case "regExp":
        return new RegExpError<T>(percentage, malformedField, params);
      default:
        throw new OperationNotSupportedException("type " + type + " not supported yet");
    }

  }

}
