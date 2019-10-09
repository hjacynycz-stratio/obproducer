package com.stratio.barclays.obproducer.application.datageneration.errors;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mifmif.common.regex.Generex;
import com.stratio.barclays.obproducer.domain.ObTransactionData;

public class RegExpError extends IntroducedError {

  public RegExpError(float percentage, String malformedField, List<String> params) {
    super(percentage, malformedField, params);
  }

  @Override
  public ObTransactionData introduceError(ObTransactionData obTransactionData)
      throws IntrospectionException, InvocationTargetException, IllegalAccessException {

    Generex generator = new Generex(params.get(0));
    new PropertyDescriptor(malformedField, ObTransactionData.class).getWriteMethod()
        .invoke(obTransactionData, generator.random());

    return obTransactionData;
  }
}
