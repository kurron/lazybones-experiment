package org.kurron.example.jpa.persistence

import groovy.transform.Canonical
import javax.persistence.Entity
import org.hibernate.annotations.Immutable as HibernateImmutable

/**
 * This is an immutable structure that is only read from the database.
 */
@Canonical
@Entity
@HibernateImmutable
class ReferenceData {

    String name
}
