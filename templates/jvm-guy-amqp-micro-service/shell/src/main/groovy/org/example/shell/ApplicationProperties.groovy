package org.example.shell

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Application specific properties.
 */
@Component
class ApplicationProperties {
    /**
     * The name of the AMQP queue where messages go to.
     */
    @Value( '${queue.name}' )
    String queue = 'unset'

    /**
     * How the shell should identify itself to the service.
     */
    @Value( '${application.id}' )
    String applicationID = 'unset'

    /**
     * Host name or ip address of the RabbitMQ server.
     */
    @Value( '${rabbitmq.host}' )
    String rabbitHost = 'unset'

    /**
     * Port of the RabbitMQ server.
     */
    @Value( '${rabbitmq.port}' )
    int rabbitPort = Integer.MIN_VALUE

    /**
     * Virtual host to use.
     */
    @Value( '${rabbitmq.virtualHost}' )
    String rabbitVirtualHost = 'unset'

    /**
     * Username to use when communicating with RabbitMQ.
     */
    @Value( '${rabbitmq.username}' )
    String rabbitUsername = 'unset'

    /**
     * Password to use when communicating with RabbitMQ.
     */
    @Value( '${rabbitmq.password}' )
    String rabbitPassword = 'unset'

}
