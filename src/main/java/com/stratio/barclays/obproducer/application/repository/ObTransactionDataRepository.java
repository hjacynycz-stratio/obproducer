package com.stratio.barclays.obproducer.application.repository;

import java.time.LocalDateTime;

import com.stratio.barclays.obproducer.domain.ObTransactionData;

public interface ObTransactionDataRepository extends EntityRepository<ObTransactionData, Long> {

  <S extends ObTransactionData> Iterable<S> findTop100ByTransactionBookingDateTimeLessThanEqualAndSentFalse(
      LocalDateTime localDateTime);

  long countBySentFalse();
}
