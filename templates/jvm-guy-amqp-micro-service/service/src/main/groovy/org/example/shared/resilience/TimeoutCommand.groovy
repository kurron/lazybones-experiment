package org.example.shared.resilience

import com.netflix.hystrix.HystrixCommand

/**
 * Resiliency wrapper that ensures the call will timeout, regardless if the integration API supports timeouts itself.
 */
@SuppressWarnings( 'AbstractClassWithoutAbstractMethod' )
abstract class TimeoutCommand<R> extends HystrixCommand<R> {

    protected TimeoutCommand( String groupKey, int timeout ) {
        super( HystrixSettingsBuilder.buildForTimeoutCommand( groupKey, timeout ) )
    }
}
