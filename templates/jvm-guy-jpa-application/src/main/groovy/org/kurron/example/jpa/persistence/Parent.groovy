package org.kurron.example.jpa.persistence

import groovy.transform.Canonical
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * This is an entity, meaning it can stand on its own, that can be associated to 1 or more children.
 */
@Canonical
@Entity
class Parent {

    @Id
    @GeneratedValue( generator = Constants.GENERATOR_STRATEGY )
    Long id

    @NotNull
    @Size( min = 1, max = 255, message = 'Name is required, 255 character maximum')
    String name
}
