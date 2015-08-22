package org.kurron.example.jpa.persistence

import groovy.transform.Canonical
import javax.persistence.Entity
import org.hibernate.annotations.Immutable as HibernateImmutable
import org.hibernate.annotations.Subselect
import org.hibernate.annotations.Synchronize

/**
 * This is an immutable structure that is only read from the database.  The data is filled using a sub-select of a
 * larger table.  Our database admin won't let us create a view!
 */
@Canonical
@Entity
@HibernateImmutable
@Subselect( value = 'select i.ID from ITEM' )
@Synchronize( ['Item', 'Bid'] )
class ReferenceData {

    String name
}
