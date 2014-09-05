package org.example.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

/**
 * Contains details when failures occur.
 */
@ToString( includePackage = false, includeNames = true )
class ErrorContext {
    /**
     * Constant that signals that the property has not been properly set.
     */
    public static final String UNSET = 'unset'

    /**
     * Short name of the error context.
     */
    @JsonProperty( 'title' )
    String title = UNSET

    /**
     * A code that uniquely identifies the error context.  Useful in operations
     * manuals.
     */
    @JsonProperty( 'code' )
    String code = UNSET

    /**
     * A detailed description of the failure and possible solutions.
     */
    @JsonProperty( 'message' )
    String message = UNSET
}
