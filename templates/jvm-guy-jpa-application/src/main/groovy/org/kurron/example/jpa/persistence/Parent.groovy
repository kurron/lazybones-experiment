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
    String doNotSaveMe = 'This should not be stored in the database'

    // this is a read-only derived property using database functions to fill the value at load time
    @Formula( 'substr( name, 1, 2 )' )
    String shortName

/*
    // we store weight in pounds but the application wants kilograms
    // be careful in queries since there is no index for this "column" -- full table scans!
    @Column( name = 'IMPERIALWEIGHT' )
    @NotNull( message = 'Weight cannot be null!' )
    @ColumnTransformer( read = 'IMPERIALWEIGHT / 2.20462', write = '? * 2.20462' )
*/
    Integer weight

/*
    @Temporal( TemporalType.TIMESTAMP )
    @HibernateGenerated( GenerationTime.ALWAYS )
*/
    Calendar lastModified

    @Column( insertable = false, columnDefinition = "varchar(255) default 'Hello, there.'" )
    @Size( min = 1, max = 255, message = 'This should have been defaulted')
    @HibernateGenerated( GenerationTime.INSERT )
    String alwaysTheSame

    @NotNull( message = 'Color cannot be null!' )
    @Enumerated( EnumType.STRING )
    Color color
}
