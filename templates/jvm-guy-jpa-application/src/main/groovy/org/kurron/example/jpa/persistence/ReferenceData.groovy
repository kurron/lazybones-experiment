package org.kurron.example.jpa.persistence

import groovy.transform.Canonical
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
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
@Subselect( value = 'select id, name from parent' )
@Synchronize( ['id', 'name'] )
class ReferenceData {

    @Id
    Long id

    @NotNull
    @Size( min = 1, max = 255, message = 'Name is required, 255 character maximum')
    String name
}
