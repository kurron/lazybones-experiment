package org.kurron.example.outbound

import groovy.util.logging.Slf4j
import org.kurron.example.ApplicationProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients

@Slf4j
@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients
@EnableConfigurationProperties( ApplicationProperties )
class FeignIntegrationTestConfiguration {

    static void main(String[] args) {
        SpringApplication.run( FeignIntegrationTestConfiguration, args )
    }

}