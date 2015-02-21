/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni

import com.transparent.asgard.feedback.FeedbackAwareBeanPostProcessor
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean

/**
 * This is the main entry into the application. Running from the command-line using embedded Tomcat will invoke
 * the main() method.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties( ApplicationProperties )
@Slf4j
@SuppressWarnings( 'GStringExpressionWithinString' )
class Application {

    /**
     * Called to start the entire application. Typically, java -jar foo.jar.
     * @param args any arguments to the program.
     */
    static void main( String[] args ) {
        log.info '--- Running embedded web container ----'
        SpringApplication.run( Application, args )
    }

    /**
     * Indicates the type of service emitting the messages.
     */
    @Value( '${spring.application.name}' )
    String serviceCode

    /**
     * Indicates the instance of the service emitting the messages.
     */
    @Value( '${PID}' )
    String serviceInstance

    /**
     * Indicates the logical group of the service emitting the messages.  For now we are
     * using the RabbitMQ vhost as a surrogate for the realm.  This works because all
     * services use an AMQP-based control bus and you want to always group your
     * services based on a common vhost.
     */
    @Value( '${spring.rabbitmq.virtualHost}' )
    String realm

    @Bean
    FeedbackAwareBeanPostProcessor feedbackAwareBeanPostProcessor() {
        new FeedbackAwareBeanPostProcessor( serviceCode, serviceInstance, realm )
    }
}
