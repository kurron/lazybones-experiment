package org.example.shell.commands.echo

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Message to send to the echo service.
 */
@Canonical
class EchoRequest {

    /**
     * Mime-Type of the request.
     */
    final String contentType = 'application/json;type=echo-request;version=1.0.0'

    /**
     * Text to store.
     */
    @JsonProperty( 'message' )
    String message
}
