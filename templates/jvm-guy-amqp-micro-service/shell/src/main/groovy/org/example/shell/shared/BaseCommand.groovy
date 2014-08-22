package org.example.shell.shared

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.shell.ApplicationProperties
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.core.CommandMarker

import java.security.SecureRandom

/**
 * Convenience base class for all shell commands that interact with the micro-service.
 */
@SuppressWarnings( ['AbstractClassWithoutAbstractMethod'] )
abstract class BaseCommand implements CommandMarker {

    /**
     * Random number generator.
     */
    protected static final SecureRandom SECURE_RANDOM = new SecureRandom()

    /**
     * JSON-to-POGO mapper.
     */
    @Autowired
    protected ObjectMapper theMapper

    /**
     * Application-specific properties.
     */
    @Autowired
    protected ApplicationProperties configuration

    /**
     * Handles AMQP communications.
     */
    @Autowired
    protected RabbitTemplate theTemplate


    protected static String generateMessageID() {
        UUID.randomUUID().toString()
    }

    protected static Date generateTimeStamp() {
        Calendar.getInstance( TimeZone.getTimeZone( 'UTC' ) ).time
    }

    protected static void randomize( byte[] buffer ) {
        SECURE_RANDOM.nextBytes( buffer )
    }

    protected static String hexString() {
        Integer.toHexString( SECURE_RANDOM.nextInt( Integer.MAX_VALUE ) ).toUpperCase()
    }

    protected static byte[] generateCorrelationID() {
        // there is some strange UTF-8 encoding going on here so don't just send randomized bytes
        UUID.randomUUID().toString().bytes
    }

    protected Message createMessage( byte [] payload,
                                     String contentType ) {
        MessageBuilder.withBody( payload )
                      .setContentType( contentType )
                      .setMessageId( generateMessageID() )
                      .setTimestamp( generateTimeStamp() )
                      .setAppId( configuration.applicationID )
                      .setCorrelationId( generateCorrelationID() )
                      .build()
    }

    protected String printJsonPayload( Message response ) {
        def json = theMapper.readValue( response.body, Object )
        theMapper.writerWithDefaultPrettyPrinter().writeValueAsString( json )
    }

    protected byte[] toJsonBytes( Object value ) {
        theMapper.writeValueAsBytes( value )
    }

}
