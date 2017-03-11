package org.kurron.example.cdc

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

/**
 * Silly implementation of the service to see if Wire Mock does its job..
 */
@Component
class DefaultOutboundService implements OutboundService {

    // would NEVER do this in production
    @Value( '${wiremock.server.port}')
    int port

    RestOperations template = new RestTemplate()

    @Override
    String go() {
        template.getForObject( "http://localhost:${port}/resource", String )
    }
}
