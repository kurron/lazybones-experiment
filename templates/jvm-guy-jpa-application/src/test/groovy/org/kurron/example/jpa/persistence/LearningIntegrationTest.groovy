package org.kurron.example.jpa.persistence

import java.sql.Blob
import javax.persistence.EntityManager
import javax.transaction.Transactional
import org.hibernate.Session
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
@Transactional
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
        Set<String> valueObjects = (1..20).collect { randomHexString() } as Set
        Parent stored = parentRepository.save( new Parent( name: randomHexString(),
                                              color: randomElement( Color.values() as List ) as Color,
                                              transformed: randomHexString(),
                                              oneToManyValueTypes: valueObjects ) )

        then: 'we can read it from the database'
        Parent read = parentRepository.findOne( stored.id )
        read
        read == stored
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

    @Autowired
    EntityManager entityManager

    def 'exercise child CRUD support'() {

        given: 'subject under test'
        assert childRepository
        assert entityManager

        when: 'record is written'
        Session session = entityManager.unwrap( Session )
        Blob blob = session.lobHelper.createBlob( randomByteArray( 64 ) )

        def address = new Address( street: randomHexString(),
                                   zipcode: randomHexString(),
                                   inserted: Calendar.instance,
                                   loadedEagerly: randomByteArray( 32 ),
                                   loadedLazily: blob )
        Child stored = childRepository.save( new Child( name: randomHexString(), address: address ) )

        then: 'we can read it from the database'
        Child read = childRepository.findOne( stored.id )
        read == stored
    }

}
