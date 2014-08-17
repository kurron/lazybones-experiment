package org.example.echo

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.support.MessageBuilder
import org.springframework.integration.support.json.JsonObjectMapper
import org.springframework.integration.transformer.Transformer
import org.springframework.messaging.Message

/**
 * Handles saving an echo request to the database.
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
        MessageBuilder.withPayload( objectMapper.fromJson( message.payload, EchoRequest ) ).copyHeaders( message.headers ).build()
    }
}
