package org.example.rest.model

import groovy.transform.ToString

/**
 * A singular instance of our example data.
 */
@ToString( includePackage = false, includeNames = true )
class Item {
    /**
     * Constant that signals that the property has not been properly set.
     */
    public static final String UNSET = 'unset'

    /**
     * Data element of the object. A real system would have a much richer set of properties.
     */
    String text = UNSET
}
