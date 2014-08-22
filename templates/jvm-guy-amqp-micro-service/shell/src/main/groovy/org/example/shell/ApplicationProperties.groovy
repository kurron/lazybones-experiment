package org.example.shell

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

/**
 * Application specific properties.
 */
@Component
@PropertySource( 'classpath:/config/application.properties' )
class ApplicationProperties {
    /**
     * The name of the AMQP queue where messages go to.
     */
    @Value( '${queue.name}' )
    String queue = 'default-queue'

}
