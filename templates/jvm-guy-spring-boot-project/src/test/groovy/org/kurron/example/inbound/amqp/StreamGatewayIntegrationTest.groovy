package org.kurron.example.inbound.amqp

import org.junit.experimental.categories.Category
import org.kurron.categories.InboundIntegrationTest
import org.kurron.example.Application
import org.kurron.example.inbound.rest.RestCapable
import org.kurron.traits.GenerationAbility
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * An integration-level test of the StreamGateway object.
 **/
@Category( InboundIntegrationTest )
@IntegrationTest
@ContextConfiguration( classes = [Application,TestConfiguration], loader = SpringApplicationContextLoader )
class StreamGatewayIntegrationTest extends Specification implements GenerationAbility, RestCapable {

    @Autowired
    RabbitAdmin administrator

    @Autowired
    StreamProducer producer

    def setup() {
        // clear the queue before each test
        assert administrator
        // administrator.purgeQueue( configuration.queueName, false )
    }

    def 'exercise publishing happy path'() {
        given: 'a proper testing environment'
        assert producer

        when: 'message is sent'
        1000.times {
            producer.send()
        }

        then:
        Thread.sleep( 1000 )
    }

}
