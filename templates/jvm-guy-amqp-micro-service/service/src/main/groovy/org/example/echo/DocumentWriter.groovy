package org.example.echo

import groovy.util.logging.Slf4j
import org.springframework.amqp.core.MessageBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.annotation.Header
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.support.json.JsonObjectMapper
import org.springframework.messaging.Message

/**
 * Handles saving an echo request to the database.
 */
@Slf4j
class DocumentWriter {

    /**
     * Manages interactions with the database.
     */
    @Autowired
    private EchoDocumentRepository theRepository

    /**
     * Manages JSON-to-POGO transformations.
     */
    @Autowired
    JsonObjectMapper objectMapper

    @ServiceActivator
    void storeDocument( EchoRequest request,
                        @Header( 'amqp_replyTo' ) String replyTo,
                        @Header( 'amqp_correlationId' ) byte[] correlationID ) {
        log.debug( 'Storing document with message {}', request.message )
        EchoDocument saved = theRepository.save( new EchoDocument( message:request.message, datetime:System.currentTimeMillis() ) )
        def response = new EchoResponse( saved.id )
        MessageBuilder.withBody( objectMapper.toJson( response ).bytes )
                      .setContentType( 'application/json;type=echo-response' )
                      .setMessageId( UUID.randomUUID().toString() )
                      .setTimestamp( Calendar.getInstance( TimeZone.getTimeZone( 'UTC' ) ).time )
                      .setAppId( 'micro-service' )
                      .setCorrelationId( correlationID )
                      .build()
    }
}
