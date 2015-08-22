package org.kurron.example.jpa.persistence

import groovy.transform.Canonical
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Transient
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import org.hibernate.annotations.ColumnTransformer
import org.hibernate.annotations.Formula
import org.hibernate.annotations.Generated as HibernateGenerated
import org.hibernate.annotations.GenerationTime

/**
 * This is an entity, meaning it can stand on its own, that can be associated to 1 or more children.
 */
@Canonical
@Entity
class Parent {

    @Id
    @GeneratedValue( generator = Constants.GENERATOR_STRATEGY )
    Long id

    @NotNull( message = 'Name cannot be null!' )
    @Size( min = 2, max = 255, message = 'Name is required, 255 character maximum')
    String name

    @Transient
    String doNotSaveMe = 'Do not store'

    // this is a read-only derived property using database functions to fill the value at load time
    @Formula( 'substr( name, 1, 2 )' )
    String calculated

    // store things in upper case but always show lower case
    @Column( name = 'TRANSFORMED' )
    @NotNull( message = 'Transformed cannot be null!' )
    @ColumnTransformer( read = 'LOWER( TRANSFORMED )', write = 'UPPER( ? )' )
    String transformed

    @Column( insertable = false, columnDefinition = "varchar(255) default 'Hello, there.'" )
    @Size( min = 1, max = 255, message = 'This should have been defaulted')
    @HibernateGenerated( GenerationTime.INSERT )
    String defaulted

    @NotNull( message = 'Color cannot be null!' )
    @Enumerated( EnumType.STRING )
    Color color
}
