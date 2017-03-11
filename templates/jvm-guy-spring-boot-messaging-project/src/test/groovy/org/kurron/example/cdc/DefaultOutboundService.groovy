package org.kurron.example.cdc

import org.springframework.stereotype.Component
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

/**
 * Silly implementation of the service to see if Wire Mock does its job..
 */
@Component
class DefaultOutboundService implements OutboundService {

    RestOperations template = new RestTemplate()

    @Override
    String go() {
        template.getForObject( 'http://localhost:1234/resource', String )
    }
}
