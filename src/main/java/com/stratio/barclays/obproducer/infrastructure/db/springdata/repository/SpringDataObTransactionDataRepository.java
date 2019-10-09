package com.stratio.barclays.obproducer.infrastructure.db.springdata.repository;

import java.time.LocalDateTime;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stratio.barclays.obproducer.application.repository.ObTransactionDataRepository;
import com.stratio.barclays.obproducer.domain.ObTransactionData;

@Repository
public interface SpringDataObTransactionDataRepository extends CrudRepository<ObTransactionData, Long>,
    ObTransactionDataRepository {

  <S extends ObTransactionData> Iterable<S> findTop100ByTransactionBookingDateTimeLessThanEqualAndSentFalse(
      LocalDateTime localDateTime);

  long countBySentFalse();

}
