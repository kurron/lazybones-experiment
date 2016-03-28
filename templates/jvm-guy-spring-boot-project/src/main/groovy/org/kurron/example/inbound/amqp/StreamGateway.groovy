package org.kurron.example.inbound.amqp

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink

/**
 * Gateway that handles incoming messages from a message queue.
 */
@EnableBinding( Sink )
class StreamGateway {

    @StreamListener( Sink.INPUT )
    void processMessage( SampleRequest  request ) {
        println "Request ${request} recieved"
        throw new UnsupportedOperationException( 'forced to fail' )
    }
}
