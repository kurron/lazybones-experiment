package org.kurron.example

import groovy.util.logging.Slf4j
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Declarable
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.context.annotation.Bean


import static org.springframework.amqp.core.Binding.DestinationType.QUEUE

/**
 * The entry point into the system.  Runs as a standalone web server.
 */
@Slf4j
@SpringBootApplication
@EnableCircuitBreaker
@EnableConfigurationProperties( ApplicationProperties )
class Application {

    static void main( String[] args ) {
        SpringApplication.run( Application, args )
    }

    @Bean
    public List<Declarable> amqpBindings( ApplicationProperties configuration ) {
        [ new DirectExchange( configuration.exchangeName ),
          new Queue( configuration.queueName ),
          new Binding( configuration.queueName, QUEUE, configuration.exchangeName, configuration.queueName, null ) ]
    }
}