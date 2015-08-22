package org.kurron.example.jpa.persistence

import groovy.transform.Canonical

/**
 * This is an immutable structure that is only read from the database.
 */
@Canonical
class ReferenceData {

    String name
}
