package org.kurron.example.jpa.persistence

import groovy.transform.Canonical
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * This is a value type, meaning it cannot exist on its own and must be associated to a parent.
 */
@Canonical
@Embeddable
class Address {

    //TODO: see if bug HVAL-3 has been fixed so we don't have to add the column annotation
    @NotNull // ignored for DDL generation so we have specify the constrain elsewhere as well
    @Column( nullable = false ) // used for DDL generation
    @Size( min = 1, max = 255, message = 'Street is required, 255 character maximum')
    String street

    @NotNull
    @Column( nullable = false ) // used for DDL generation
    @Size( min = 1, max = 255, message = 'Zip Code is required, 255 character maximum')
    String zipcode
}
