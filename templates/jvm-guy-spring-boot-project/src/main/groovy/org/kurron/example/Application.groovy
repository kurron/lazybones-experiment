package org.kurron.example

import groovy.util.logging.Slf4j
import org.aopalliance.aop.Advice
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.Declarable
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor

import static org.springframework.amqp.core.Binding.DestinationType.QUEUE

/**
 * The entry point into the system.  Runs as a standalone web server.
 */
@Slf4j
@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableConfigurationProperties( ApplicationProperties )
class Application {

    static void main( String[] args ) {
        SpringApplication.run( Application, args )
    }

    @Bean
    public List<Declarable> amqpBindings( ApplicationProperties configuration ) {
        [
          new DirectExchange( configuration.exchangeName ),
          new Queue( configuration.queueName ),
          new Binding( configuration.queueName, QUEUE, configuration.exchangeName, configuration.queueName, null ),
          new DirectExchange( configuration.deadLetterExchangeName ),
          new Queue( configuration.deadLetterQueueName ),
          new Binding( configuration.deadLetterQueueName, QUEUE, configuration.deadLetterExchangeName, configuration.deadLetterQueueName, null )
        ] as List<Declarable>
    }

    @Bean
    StatefulRetryOperationsInterceptor interceptor( RabbitTemplate template, ApplicationProperties settings ) {
        def strategy = new RepublishMessageRecoverer( template, 'dead-letter', 'dead-letter' )
        RetryInterceptorBuilder.stateful()
                .maxAttempts( settings.messageRetryAttempts )
                .backOffPolicy( new ExponentialRandomBackOffPolicy() )
                .recoverer( strategy )
                .build()
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        new Jackson2JsonMessageConverter()
    }


    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory( Jackson2JsonMessageConverter converter,
                                                                         ConnectionFactory connectionFactory,
                                                                         StatefulRetryOperationsInterceptor interceptor ) {
        // for some reason, the Spring Boot auto configuration does not enable JSON serialization so we do it here
        new SimpleRabbitListenerContainerFactory().with {
            setMessageConverter( converter )
            setConnectionFactory( connectionFactory )
            setAdviceChain( [interceptor] as Advice[] )
            it
        }
    }
}