package org.kurron.example.inbound.amqp

import groovy.util.logging.Slf4j
import org.kurron.stereotype.InboundGateway
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink

/**
 * Gateway that handles incoming messages from a message queue.
 */
@Slf4j
@InboundGateway
@EnableBinding( Sink )
class StreamGateway {

    @StreamListener( Sink.INPUT )
    void processMessage( String  request ) {
        log.info( 'Hearing {}', request )
    }
}
