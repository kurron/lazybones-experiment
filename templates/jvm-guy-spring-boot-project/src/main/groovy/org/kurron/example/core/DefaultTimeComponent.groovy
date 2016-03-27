package org.kurron.example.core

import com.netflix.spectator.api.Registry
import org.kurron.example.outbound.TimeService
import org.kurron.feedback.AbstractFeedbackAware
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.Instant
import java.util.concurrent.Callable

/**
 * An example of a core component.  Remember, components can only interact with
 * other components and outbound gateways.  All the work they do is done in-memory,
 * no touching the network, filesystem or anything else that exists outside the process.
 **/
@Component
class DefaultTimeComponent extends AbstractFeedbackAware implements TimeComponent, GenerationAbility {

    /**
     * Outbound gateway.
     */
    private final TimeService gateway

    /**
     * Metrics collector.
     */
    private final Registry registry

    @Autowired
    DefaultTimeComponent( TimeService aGateway, Registry aRegistry ) {
        gateway = aGateway
        registry = aRegistry
    }

    @Override
    Instant currentTime() {
        // the timer also counts so this is redundant
        def counter = registry.counter( 'currentTimeCounter' )
        counter.increment()

        // contrived example: normally this is used to record some incoming value
        def distribution = registry.distributionSummary( 'currentTimeDistribution' )
        distribution.record( randomLong() )

        // gauges, which track the current number of something, like queue size, are also available

        def timer = registry.timer( 'currentTimeTimer' )
        // in a real implementation we would interact with multiple services and take
        // the best result but this is only an example
        // The timer simultaneously records 4 statistics: count, max, totalOfSquares, and totalTime.
        timer.record( { gateway.checkTheTime() } as Callable<Instant> )
    }
}
