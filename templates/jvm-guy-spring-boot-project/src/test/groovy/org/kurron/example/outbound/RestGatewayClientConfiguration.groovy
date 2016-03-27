package org.kurron.example.outbound

import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Custom Feign configuration for the RestGatewayClient.
 *
 * A warning from the Spring Cloud documentation:
 *
 * The RestGatewayClientConfiguration has to be @Configuration but take care that it is not in a
 * @ComponentScan for the main application context, otherwise it will be used for every @FeignClient. If you
 * use @ComponentScan (or @SpringBootApplication) you need to take steps to avoid it being included (for
 * instance put it in a separate, non-overlapping package, or specify the packages to scan explicitly
 * in the @ComponentScan).
 */
@Configuration
class RestGatewayClientConfiguration {

    @Bean
    RestGatewayClientFallback restGatewayClientFallback() {
        new RestGatewayClientFallback()
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        Logger.Level.FULL
    }
}
