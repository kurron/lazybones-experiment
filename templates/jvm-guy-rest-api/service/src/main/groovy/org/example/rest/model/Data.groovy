package org.example.rest.model

import groovy.transform.ToString

/**
 * Represents a single field in a data structure. Used to guide the API user in how to fill
 * out the forms for POST and PUT requests.
 */
@ToString( includePackage = false, includeNames = true )
class Data {
    /**
     * Constant that signals that the property has not been properly set.
     */
    public static final String UNSET = 'unset'

    /**
     * The name of data field -- required.
     */
    String name = UNSET

    /**
     * The value of data field -- optional.  A production system would likely come up with
     * a typing system to help guide the caller, eg the value is expected to be a NUMBER,
     * or a BOOLEAN, or NULL.  Our sample resource only deals with String data so this will
     * suffice.
     */
    String value = UNSET

    /**
     * The prompt to use when asking the user to fill in the value -- optional.
     */
    String prompt = UNSET

}
