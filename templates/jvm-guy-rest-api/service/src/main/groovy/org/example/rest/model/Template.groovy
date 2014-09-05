package org.example.rest.model

import groovy.transform.ToString

/**
 * The template object contains all of the input elements used to add or edit collection records.
 */
@ToString( includePackage = false, includeNames = true )
class Template {
    List<Data> data
}
