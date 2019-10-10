package com.stratio.barclays.obproducer.application.datageneration.errors;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mifmif.common.regex.Generex;

public class RegExpError<T> extends IntroducedError<T> {

  public RegExpError(float percentage, String malformedField, List<String> params) {
    super(percentage, malformedField, params);
  }

  @Override
  public T introduceError(T data)
      throws IntrospectionException, InvocationTargetException, IllegalAccessException {

    Generex generator = new Generex(params.get(0));
    new PropertyDescriptor(malformedField, data.getClass()).getWriteMethod()
        .invoke(data, generator.random());

    return data;
  }
}
