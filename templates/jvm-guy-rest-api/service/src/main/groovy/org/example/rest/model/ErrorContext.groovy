package org.example.rest.model

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
    String title = UNSET

    /**
     * A code that uniquely identifies the error context.  Useful in operations
     * manuals.
     */
    String code = UNSET

    /**
     * A detailed description of the failure and possible solutions.
     */
    String message = UNSET
}
