package com.stratio.barclays.obproducer.application.service;

public interface EventProducer<T> {

  void send(T eventToProduce);

}
