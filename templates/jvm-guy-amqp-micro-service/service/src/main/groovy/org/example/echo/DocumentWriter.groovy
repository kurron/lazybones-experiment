package org.example.echo

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.support.MessageBuilder
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
    JsonObjectMapper theObjectMapper

    /**
     * Store the data into the database and returns the primary key of the document.
     * @param request data to store.
     * @return a JSON response containing the newly created document's primary key.
     */
    @ServiceActivator
    Message<byte[]> storeDocument( EchoRequest request ) {
        log.debug( 'Storing document with message {}', request.message )
        EchoDocument saved = theRepository.save( new EchoDocument( message:request.message, datetime:System.currentTimeMillis() ) )
        def response = new EchoResponse( saved.id )
        MessageBuilder.withPayload( theObjectMapper.toJson( response ).bytes )
                      .setHeaderIfAbsent( 'contentType', 'application/json;type=echo-response' ).build()
    }
}