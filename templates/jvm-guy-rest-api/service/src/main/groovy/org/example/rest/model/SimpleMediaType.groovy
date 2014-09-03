package org.example.rest.model

import groovy.transform.Canonical

/**
 * A hypermedia control that supports all the states of the sample resource.
 */
@Canonical
class SimpleMediaType {

    /**
     * Option field that will be filled when a singular instance is requested.
     */
    Item item

    /**
     * Optional field that will be filled in only when error states are reached.
     */
    ErrorContext error
}
