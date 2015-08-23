package org.kurron.example.jpa.persistence

import groovy.transform.Canonical
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import org.hibernate.annotations.Type as HibernateType
/**
 * This is an entity, meaning it can stand on its own, that can be associated to 1 or more parents.
 */
@Canonical
@Entity
class Child {

    @Id
    @GeneratedValue( generator = Constants.GENERATOR_STRATEGY )
    Long id

    @NotNull
    @Size( min = 1, max = 255, message = 'Name is required, 255 character maximum')
    String name

    // no annotation needed because the Address class is annotated
    Address address

    @HibernateType( type = 'yes_no' )
    boolean useYesNo
}
