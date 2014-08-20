package org.example.shared.resilience

import com.netflix.hystrix.HystrixCommand

/**
 * Resiliency wrapper that ensures the call will timeout, regardless if the integration API supports timeouts itself.
 */
abstract class TimeoutCommand<R> extends HystrixCommand<R> {

    TimeoutCommand( String groupKey, int timeout ) {
        super( HystrixSettingsBuilder.build( groupKey, timeout ) )
    }
}
