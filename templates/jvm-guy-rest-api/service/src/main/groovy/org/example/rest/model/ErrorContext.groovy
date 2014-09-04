package org.example.rest.model

import groovy.transform.Canonical

/**
 * Contains details when failures occur.
 */
@Canonical
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
