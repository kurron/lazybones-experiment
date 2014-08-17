package org.example.echo

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.support.MessageBuilder
import org.springframework.integration.support.json.JsonObjectMapper
import org.springframework.integration.transformer.Transformer
import org.springframework.messaging.Message

/**
 * Handles converting the raw message payload into a POGO.
 */
@Slf4j
class BytesToEchoRequestTransformer implements Transformer {

    /**
     * Manages JSON-to-POGO transformations.
     */
    @Autowired
    JsonObjectMapper objectMapper

    @Override
    Message<?> transform( Message<?> message) {
        log.debug( 'Transforming byte payload into request' )
        // a more robust implementation would verify the content-type before proceeding
        MessageBuilder.withPayload( objectMapper.fromJson( message.payload, EchoRequest ) ).copyHeaders( message.headers ).build()
    }
}
