package org.example.configuration

import com.netflix.hystrix.HystrixCircuitBreaker
import com.netflix.hystrix.HystrixCommandKey
import org.example.shared.resilience.Gateways
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator

/**
 * Custom health indicator which checks circuit breakers and runs smoke tests to verify things are operating
 * properly.
 */
class HealthController implements HealthIndicator {

    @Override
    Health health() {
        def status = Health.up()
        Map<String,String> breakerStatus = [:]
        Gateways.values().each {
            def breaker = HystrixCircuitBreaker.Factory.getInstance( HystrixCommandKey.Factory.asKey( it.name() ) )
            breakerStatus[it.toString()] = breaker?.open ? 'DOWN' : 'UP'
        }
        status.withDetail( 'circuit-breakers', breakerStatus )
        status.build()
    }
}
