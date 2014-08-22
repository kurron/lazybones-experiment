package org.example.shell.shared.resilience;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * This class exists solely because Groovy could not handle the mixture of Hystrix's static inner classes.
 * Dropping down to Java fixes the problem.  These setting combinations should only be considered examples of what
 * can be done with Hystrix.  You can combine all sorts of settings including:
 * <ul>
 *     <li>isolation strategy (defaults to thread pool)</li>
 *     <li>timeouts (defaults to 1000ms)</li>
 *     <li>number of concurrent requests (defaults to 10)</li>
 *     <li>circuit breaker error threshold percentages (defaults to 50%)</li>
 *     <li>how long to wait before retrying an open breaker (defaults to 5000ms)</li>
 *     <li>thread pool size (defaults to 10)</li>
 *     <li>maximum queue size (defaults to -1)</li>
 *     <li>queue rejection threshold (defaults to 5)</li>
 * </ul>
 */
public class HystrixSettingsBuilder {

    /**
     * Not intended to be instantiated.
     */
    private HystrixSettingsBuilder() {}

    /**
     * Builds the necessary Hystrix configuration instance using mostly defaulted values.
     * @param command the command key to associate the command to.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter buildUsingDefaults( final String command ) {
        final HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey( "example" );
        final HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey( command );
        final HystrixThreadPoolKey poolKey = HystrixThreadPoolKey.Factory.asKey( command );
        final HystrixCommandProperties.Setter defaults = HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds( 2000 );
        return HystrixCommand.Setter.withGroupKey( groupKey ).andCommandKey( commandKey).andThreadPoolKey( poolKey ).andCommandPropertiesDefaults( defaults );
    }
}
