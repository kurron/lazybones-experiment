package org.example.rest.model

import groovy.transform.Canonical

/**
 * A singular instance of our example data.
 */
@Canonical
class Item {
    /**
     * The unique identifier of this instance.
     */
    String instance = 'unset'

    /**
     * Data element of the object..
     */
    String text = 'unset'
}
