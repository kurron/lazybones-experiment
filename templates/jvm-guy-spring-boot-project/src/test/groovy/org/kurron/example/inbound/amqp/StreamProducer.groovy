package org.kurron.example.inbound.amqp

import groovy.util.logging.Slf4j
import org.kurron.stereotype.OutboundGateway
import org.kurron.traits.GenerationAbility
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.integration.annotation.InboundChannelAdapter

import java.time.Instant

/**
 * Test message producer.
 */
@Slf4j
@OutboundGateway
@EnableBinding( Source )
class StreamProducer implements GenerationAbility{

    @InboundChannelAdapter( Source.OUTPUT )
    public String send() {
        def message = "Hello, World. It is ${Instant.now().toString()}"
        log.info( 'Sending {}', message )
        message
    }
}
