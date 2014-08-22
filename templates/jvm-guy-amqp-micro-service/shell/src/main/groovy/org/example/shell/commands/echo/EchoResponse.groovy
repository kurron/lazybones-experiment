package org.example.shell.commands.echo

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Message returned from the echo service.
 */
@Canonical
class EchoResponse {

    /**
     * Unique identifier of the stored message.
     */
    @JsonProperty( 'id' )
    String id
}
