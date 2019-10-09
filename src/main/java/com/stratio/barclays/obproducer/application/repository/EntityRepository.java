package com.stratio.barclays.obproducer.application.repository;

import java.util.Optional;

public interface EntityRepository<T, ID> {

  <S extends T> S save(S s);

  <S extends T> Iterable<S> saveAll(Iterable<S> iterable);

  Optional<T> findById(ID aLong);

  boolean existsById(ID aLong);

  Iterable<T> findAll();

  Iterable<T> findAllById(Iterable<ID> iterable);

  long count();

  void deleteById(ID aLong);

  void delete(T entity);

  void deleteAll(Iterable<? extends T> iterable);

  void deleteAll();

}
