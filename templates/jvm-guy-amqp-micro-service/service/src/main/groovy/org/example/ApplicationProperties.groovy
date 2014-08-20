package org.example

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Application specific properties.  This can be injected into beans to share values.
 */
@ConfigurationProperties( value = 'example', ignoreUnknownFields = false )
class ApplicationProperties {
    /**
     * The name of the AMQP queue where messages go to.
     */
    String queue = 'echo-queue'

    /**
     * A short but unique identifier of this service.  Will become part of any logged messages.
     * Useful in filtering through messages.
     */
    String applicationType = 'BEEF'
}
