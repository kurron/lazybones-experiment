package org.example.rest.model

import groovy.transform.Canonical

/**
 * A singular instance of our example data.
 */
@Canonical
class Item {
    /**
     * Constant that signals that the property has not been properly set.
     */
    public static final String UNSET = 'unset'

    /**
     * The unique identifier of this instance.
     */
    String instance = UNSET

    /**
     * Data element of the object..
     */
    String text = UNSET
}
