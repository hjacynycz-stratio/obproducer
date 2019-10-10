package com.stratio.barclays.obproducer.application.datageneration.errors;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class IntroducedError<T> {

  protected final float percentage;

  protected final String malformedField;

  protected final List<String> params;

  public abstract T introduceError(T data)
      throws IntrospectionException, InvocationTargetException, IllegalAccessException;
}
