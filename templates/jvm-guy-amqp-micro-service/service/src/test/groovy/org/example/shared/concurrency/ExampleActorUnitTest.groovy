package org.example.shared.concurrency

import org.example.shared.BaseUnitTest

import java.util.concurrent.TimeUnit

/**
 * Unit test of actor-based concurrency object.
 */
class ExampleActorUnitTest extends BaseUnitTest {

    def 'exercise message passing'() {
        given: 'a couple of subjects under test'
        def depp = new HollywoodActor( name: 'Johnny Depp' )
        def hanks = new HollywoodActor( name: 'Tom Hanks' )

        and: 'the subjects are activated'
        [depp,hanks]*.start()

        when: 'messages are sent'
        depp.send( 'Wonka')
        hanks.send( 'Lovell' )

        and: 'we wait for a second'
        [depp,hanks]*.join( 1, TimeUnit.SECONDS )

        then: 'messages are printed to the screen'
        true
    }

}
