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
    ParentRepository sut

    def 'exercise CRUD support'() {

        given: 'subject under test'
        assert sut

        when: 'record is written'
        Parent stored = sut.save( new Parent( name: randomHexString(),
                                              color: randomElement( Color.values() as List ) as Color ) )

        then: 'we can read it from the database'
        Parent read = sut.findOne( stored.id )
        read == stored
    }
}
