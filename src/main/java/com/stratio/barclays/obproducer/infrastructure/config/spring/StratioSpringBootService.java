package com.stratio.barclays.obproducer.infrastructure.config.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.stratio.barclays.obproducer.infrastructure")
@EntityScan(basePackages = "com.stratio.barclays.obproducer.domain")
public class StratioSpringBootService {

  public static void main(String[] args) {
    SpringApplication.run(StratioSpringBootService.class, args);
  }
}