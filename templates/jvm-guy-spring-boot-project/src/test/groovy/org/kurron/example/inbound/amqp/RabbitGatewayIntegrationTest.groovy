package org.kurron.example.inbound.amqp

import org.junit.experimental.categories.Category
import org.kurron.categories.InboundIntegrationTest
import org.kurron.example.Application
import org.kurron.example.ApplicationProperties
import org.kurron.example.inbound.rest.RestCapable
import org.kurron.traits.GenerationAbility
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * An integration-level test of the RabbitGateway object.
 **/
@Category( InboundIntegrationTest )
@IntegrationTest
@ContextConfiguration( classes = [Application,TestConfiguration], loader = SpringApplicationContextLoader )
class RabbitGatewayIntegrationTest extends Specification implements GenerationAbility, RestCapable {

    @Autowired
    ApplicationProperties configuration

    @Autowired
    RabbitAdmin administrator

    def setup() {
        // clear the queue before each test
        administrator.purgeQueue( configuration.queueName, false )
    }

    def 'exercise GET happy path'() {
        given: 'a proper testing environment'
        assert administrator

        when: 'we GET /descriptor/application'

        then: 'we get a proper response'
    }

}
