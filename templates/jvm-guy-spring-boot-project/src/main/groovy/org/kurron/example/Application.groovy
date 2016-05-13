/*
 * Copyright (c) 2016. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kurron.example

import static org.springframework.amqp.core.Binding.DestinationType.QUEUE
import groovy.util.logging.Slf4j
import org.aopalliance.aop.Advice
import org.kurron.example.shared.ApplicationProperties
import org.kurron.feedback.FeedbackAwareBeanPostProcessor
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
import org.springframework.context.annotation.Bean
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor

/**
 * The entry point into the system.  Runs as a standalone web server.
 */
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties( ApplicationProperties )
class Application {

    static void main( String[] args ) {
        SpringApplication.run( Application, args )
    }

    @Bean
    static FeedbackAwareBeanPostProcessor feedbackAwareBeanPostProcessor( ApplicationProperties configuration ) {
        new FeedbackAwareBeanPostProcessor( configuration.logging.serviceCode, configuration.logging.serviceInstance, configuration.logging.realm )
    }

    @SuppressWarnings( 'UnnecessaryCast' )
    @Bean
    List<Declarable> amqpBindings( ApplicationProperties configuration ) {
        [
                new DirectExchange( configuration.inbound.exchangeName ),
                new Queue( configuration.inbound.queueName ),
                new Binding( configuration.inbound.queueName, QUEUE, configuration.inbound.exchangeName, configuration.inbound.routingKey, null ),
                new DirectExchange( configuration.deadletter.exchangeName ),
                new Queue( configuration.deadletter.queueName ),
                new Binding( configuration.deadletter.queueName, QUEUE, configuration.deadletter.exchangeName, configuration.deadletter.routingKey, null )
        ] as List<Declarable>
    }

    @Bean
    StatefulRetryOperationsInterceptor interceptor( RabbitTemplate template, ApplicationProperties settings ) {
        def strategy = new RepublishMessageRecoverer( template, settings.deadletter.exchangeName, settings.deadletter.routingKey )
        RetryInterceptorBuilder.stateful()
                               .maxAttempts( settings.deadletter.messageRetryAttempts )
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
