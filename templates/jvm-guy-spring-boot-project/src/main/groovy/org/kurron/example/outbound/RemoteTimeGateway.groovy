package org.kurron.example.outbound

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.kurron.example.MessagingContext
import org.kurron.feedback.AbstractFeedbackAware
import org.kurron.stereotype.OutboundGateway

import java.time.Instant

/**
 * A "real" implementation of the time service that consults with an official authority.
 **/
@SuppressWarnings( 'GroovyUnusedDeclaration' )
@OutboundGateway
class RemoteTimeGateway extends AbstractFeedbackAware implements TimeService {

    @HystrixCommand( fallbackMethod = 'defaultTime' )
    @Override
    Instant checkTheTime() {
        // see FeignIntegrationTest for an example on a better way to
        // make HTTP calls

        // force an error to trigger the circuit-breaker
        def e = new UnsupportedOperationException( 'checkTime' )
        feedbackProvider.sendFeedback( MessagingContext.FORCED_ERROR, 1964 )
        throw e
    }

    @SuppressWarnings( ['GrMethodMayBeStatic'] )
    private Instant defaultTime() {
        // looks like we can't reach the time server, let's return our local time instead
        Instant.now()
    }
}
