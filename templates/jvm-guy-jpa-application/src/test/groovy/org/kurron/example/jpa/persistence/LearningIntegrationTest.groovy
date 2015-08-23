package org.kurron.example.jpa.persistence

import org.kurron.example.jpa.Application
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * A test to see how Spring Data JPA and Spring 4 work together.
 */
@ContextConfiguration( loader = SpringApplicationContextLoader, classes = Application )
class LearningIntegrationTest extends Specification implements GenerationAbility {

    @Autowired
    ParentRepository parentRepository

    @Autowired
    ReferenceDataRepository referenceDataRepository

    @Autowired
    ChildRepository childRepository

    def 'exercise parent CRUD support'() {

        given: 'subject under test'
        assert parentRepository

        when: 'record is written'
        Parent stored = parentRepository.save( new Parent( name: randomHexString(),
                                              color: randomElement( Color.values() as List ) as Color,
                                              transformed: randomHexString() ) )

        then: 'we can read it from the database'
        Parent read = parentRepository.findOne( stored.id )
        read
        // they will never be equal due to the calculate field
        // read == stored
    }

    def 'exercise immutable entity support'() {

        given: 'subject under test'
        assert parentRepository
        assert referenceDataRepository

        when: 'record is written'
        Parent stored = parentRepository.save( new Parent( name: randomHexString(),
                                                           color: randomElement( Color.values() as List ) as Color,
                                                           transformed: randomHexString() ) )

        then: 'we can read it from the database'
        ReferenceData read = referenceDataRepository.findOne( stored.id )
        read.name == stored.name
    }

    def 'exercise child CRUD support'() {

        given: 'subject under test'
        assert childRepository

        when: 'record is written'
        def address = new Address( street: randomHexString(),
                                   zipcode: randomHexString(),
                                   inserted: Calendar.instance,
                                   loadedEagerly: randomByteArray( 32 ))
        Child stored = childRepository.save( new Child( name: randomHexString(), address: address ) )

        then: 'we can read it from the database'
        Child read = childRepository.findOne( stored.id )
        read == stored
    }

}
