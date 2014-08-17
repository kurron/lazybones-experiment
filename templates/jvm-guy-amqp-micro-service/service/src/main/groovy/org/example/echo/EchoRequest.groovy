package org.example.echo

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Message to send to the echo service.
 */
@Canonical
class EchoRequest {

    /**
     * Text to store.
     */
    @JsonProperty( 'message' )
    String message
}
