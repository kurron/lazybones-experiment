package org.kurron.example.inbound.amqp

import groovy.util.logging.Slf4j
import org.kurron.stereotype.OutboundGateway
import org.kurron.traits.GenerationAbility
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.InboundChannelAdapter

import java.time.Instant

/**
 * Test message producer.
 */
@Slf4j
@OutboundGateway
class StreamProducer implements GenerationAbility{

    @InboundChannelAdapter( Processor.OUTPUT )
    public String send() {
        def message = "Hello, World. It is ${Instant.now().toString()}"
        log.info( 'Sending {}', message )
        message
    }
}
