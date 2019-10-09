package com.stratio.barclays.obproducer.infrastructure.rest.spring.resource

import com.stratio.barclays.obproducer.infrastructure.config.spring.StratioSpringBootService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification


@SpringBootTest(classes = StratioSpringBootService.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EmbeddedKafka
abstract class ResourceISpec extends Specification {

  @Autowired
  protected MockMvc mockMvc

}
