package com.stratio.barclays.obproducer.infrastructure.db.springdata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.stratio.barclays.obproducer.infrastructure.db.springdata.repository")
public class SpringDataConfig {

}
