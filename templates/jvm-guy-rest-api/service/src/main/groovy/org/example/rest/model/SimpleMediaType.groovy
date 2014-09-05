package org.example.rest.model

/**
 * A hypermedia control that supports all the states of the sample resource.
 */
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

