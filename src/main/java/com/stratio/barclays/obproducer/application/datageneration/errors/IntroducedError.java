package com.stratio.barclays.obproducer.application.datageneration.errors;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.stratio.barclays.obproducer.domain.ObTransactionData;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class IntroducedError {

  protected final float percentage;

  protected final String malformedField;

  protected final List<String> params;

  public abstract ObTransactionData introduceError(ObTransactionData obTransactionData)
      throws IntrospectionException, InvocationTargetException, IllegalAccessException;
}
