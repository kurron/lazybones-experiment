package org.example.echo

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.example.ApplicationProperties
import org.example.shared.BaseStepDefinition
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.support.json.JsonObjectMapper

/**
 * Implementation of the upload message scenario.
 */
class SendAMessageStepDefinitions extends BaseStepDefinition {

    /**
     * Application-specific properties.
     */
    @Autowired
    ApplicationProperties properties

    /**
     * Manages interactions with RabbitMQ.
     */
    @Autowired
    RabbitOperations rabbitOperations

    /**
     * Manages interactions with MongoDB.
     */
    @Autowired
    EchoDocumentRepository repository

    /**
     * Manages JSON-to-POGO transformations.
     */
    @Autowired
    JsonObjectMapper objectMapper

    /**
     * Message to send.
     */
    EchoRequest request

    /**
     * Message to receive.
     */
    EchoResponse response

    @Given( '^a text message I want to store$' )
    void a_text_message_I_want_to_store() throws Throwable {
        request = new EchoRequest( UUID.randomUUID().toString() )
    }

    @When( '^I call the echo message service$' )
    void i_call_the_echo_message_service() throws Throwable {
        def message = MessageBuilder.withBody( objectMapper.toJson( request ).bytes )
                                    .setContentType( 'application/json;type=echo-request' )
                                    .setMessageId( UUID.randomUUID().toString() )
                                    .setTimestamp( Calendar.getInstance( TimeZone.getTimeZone( 'UTC' ) ).time )
                                    .setAppId( 'send-test' )
                                    .setCorrelationId( UUID.randomUUID().toString().bytes )
                                    .build()
        // use the default exchange and the routing key is the queue name
        def responseMessage = rabbitOperations.sendAndReceive( properties.queue, message )
        response = objectMapper.fromJson( responseMessage.body, EchoResponse )
    }

    @Then( '^my message should be stored in the system$' )
    void my_message_should_be_stored_in_the_system() throws Throwable {
        assert response
        def found = repository.findOne( response.id )
        assert found
        assert found.message == request.message
    }
}
