package org.example.shared.resilience

import com.netflix.hystrix.HystrixCommand

/**
 * Resiliency wrapper that will prevent interactions with a faulty resource.
 */
@SuppressWarnings( 'AbstractClassWithoutAbstractMethod' )
abstract class CircuitBreakerCommand<R> extends HystrixCommand<R> {

    protected CircuitBreakerCommand( String groupKey, String commandKey, boolean forceOpen ) {
        super( HystrixSettingsBuilder.buildForCircuitBreakerCommand( groupKey, commandKey, forceOpen ) )
    }
}
