package org.example.configuration

import static org.example.shared.feedback.CustomFeedbackContext.SYNTHETIC_TRANSACTION_FAILURE

import com.netflix.hystrix.HystrixCircuitBreaker
import com.netflix.hystrix.HystrixCommandKey
import org.example.ApplicationProperties
import org.example.echo.EchoRequest
import org.example.echo.EchoResponse
import org.example.shared.feedback.BaseFeedbackAware
import org.example.shared.resilience.Gateways
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.integration.support.json.JsonObjectMapper

/**
 * Custom health indicator which checks circuit breakers and runs smoke tests to verify things are operating
 * properly.
 */
class HealthController extends BaseFeedbackAware implements HealthIndicator {

    /**
     * Application-specific properties.
     */
    @Autowired
    private ApplicationProperties applicationProperties

    /**
     * Manages interactions with RabbitMQ.
     */
    @Autowired
    private RabbitOperations rabbitOperations

    /**
     * Manages JSON-to-POGO transformations.
     */
    @Autowired
    private JsonObjectMapper objectMapper

    @Override
    Health health() {
        def status = Health.up()
        Map<String,String> breakerStatus = [:]
        Gateways.values().each {
            def breaker = HystrixCircuitBreaker.Factory.getInstance( HystrixCommandKey.Factory.asKey( it.name() ) )
            breakerStatus[it.toString()] = breaker?.open ? 'DOWN' : 'UP'
        }
        status.withDetail( 'circuit-breakers', breakerStatus )
        status.withDetail( 'synthetic-transactions', executeSyntheticTransaction() ? 'UP' : 'DOWN' )

        status.build()
    }

    @SuppressWarnings( 'CatchException' )
    private boolean executeSyntheticTransaction() {
        boolean successful = false
        try {
            def request = new EchoRequest( UUID.randomUUID().toString() )
            def message = MessageBuilder.withBody( objectMapper.toJson( request ).bytes )
                                        .setContentType( 'application/json;type=echo-request' )
                                        .setMessageId( UUID.randomUUID().toString() )
                                        .setTimestamp( Calendar.getInstance( TimeZone.getTimeZone( 'UTC' ) ).time )
                                        .setAppId( 'health check' )
                                        .setCorrelationId( UUID.randomUUID().toString().bytes )
                                        .build()
            // use the default exchange and the routing key is the queue name
            def response = rabbitOperations.sendAndReceive( applicationProperties.queue, message )
            EchoResponse echoResponse = objectMapper.fromJson( response.body, EchoResponse )
            successful = echoResponse != null
        }
        catch ( Exception e ) {
            successful = false
            feedbackProvider.sendFeedback( SYNTHETIC_TRANSACTION_FAILURE, e )
        }
        finally {
            successful
        }
    }
}
