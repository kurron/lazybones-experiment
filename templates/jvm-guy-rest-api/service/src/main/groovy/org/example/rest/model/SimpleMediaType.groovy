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
     * Option field that will be filled when multiple instances are requested. A production system
     * would likely have the notion of 'shallow' and 'deep' collections.  The 'shallow' versions would
     * contain only a portion of the Item's data and URIs to load the full details.
     */
    List<Item> collection

    /**
     * Optional field that will be filled in only when error states are reached.
     */
    ErrorContext error

    /**
     * Optional field used to insert or update data in the system. Another alternative might be to use a blank Item
     * object instead but you are trading the dynamic attributes for typing and, possibly, better validation.
     */
    Template template
}

