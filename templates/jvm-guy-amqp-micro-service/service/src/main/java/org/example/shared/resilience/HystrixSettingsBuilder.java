package org.example.shared.resilience;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * This class exists solely because Groovy could not handle the mixture of Hystrix's static inner classes.
 * Dropping down to Java fixes the problem.
 */
public class HystrixSettingsBuilder {

    /**
     * Not intended to be instantiated.
     */
    private HystrixSettingsBuilder() {}

    /**
     * Builds the necessary Hystrix configuration instance.
     * @param group the group key to associate the command to.
     * @param command the command key to associate the command to.
     * @param timeout the duration, in milliseconds, to wait for the command to complete before cancelling it and timing out.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter build( final String group, final String command, final int timeout ) {
        final HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey( group );
        final HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey( command );
        final HystrixThreadPoolKey poolKey = HystrixThreadPoolKey.Factory.asKey( command );
        final HystrixCommandProperties.Setter defaults = HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds( timeout );
        return HystrixCommand.Setter.withGroupKey( groupKey ).andCommandKey( commandKey).andThreadPoolKey( poolKey ).andCommandPropertiesDefaults( defaults );
    }

    /**
     * Builds the necessary Hystrix configuration instance.
     * @param groupKey the group key to associate the command to.
     * @param timeout the duration, in milliseconds, to wait for the command to complete before cancelling it and timing out.
     * @return properly constructed Hystrix settings.
     */
    public static HystrixCommand.Setter buildForTimeoutCommand(String groupKey, int timeout) {
        return HystrixCommand.Setter.withGroupKey( HystrixCommandGroupKey.Factory.asKey( groupKey ) )
                             .andCommandPropertiesDefaults( HystrixCommandProperties.Setter()
                             .withExecutionIsolationThreadTimeoutInMilliseconds( timeout ) );

    }
}
