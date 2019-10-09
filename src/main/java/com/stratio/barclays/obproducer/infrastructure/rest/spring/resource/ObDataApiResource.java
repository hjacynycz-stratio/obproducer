package com.stratio.barclays.obproducer.infrastructure.rest.spring.resource;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.stratio.barclays.obproducer.application.service.ObLogic;
import com.stratio.barclays.obproducer.infrastructure.rest.spring.spec.ObdataApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ObDataApiResource implements ObdataApi {

  private final ObLogic obLogic;

  @Override
  public ResponseEntity<Void> obdataPost() {

    CompletableFuture.runAsync(obLogic::generateTransactionData);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
