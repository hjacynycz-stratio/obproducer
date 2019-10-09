package com.stratio.barclays.obproducer.application.datageneration.generator;

import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import uk.co.jemos.podam.common.AttributeStrategy;

public class RandomLocalDateTime implements AttributeStrategy<String> {

  @Override
  public String getValue(Class<?> aClass, List<Annotation> list) {

    long minDate = LocalDateTime.of(1970, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
    long maxDate = LocalDateTime.of(2020, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
    long randomEpochSecond = ThreadLocalRandom.current().nextLong(minDate, maxDate);
    return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC).toString();
  }
}
