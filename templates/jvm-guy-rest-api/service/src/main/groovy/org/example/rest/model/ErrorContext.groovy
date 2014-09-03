package org.example.rest.model

import groovy.transform.Canonical

/**
 * Contains details when failures occur.
 */
@Canonical
class ErrorContext {
    /**
     * Short name of the error context.
     */
    String title = 'unset'

    /**
     * A code that uniquely identifies the error context.  Useful in operations
     * manuals.
     */
    String code = 'unset'

    /**
     * A detailed description of the failure and possible solutions.
     */
    String message = 'unset'
}
