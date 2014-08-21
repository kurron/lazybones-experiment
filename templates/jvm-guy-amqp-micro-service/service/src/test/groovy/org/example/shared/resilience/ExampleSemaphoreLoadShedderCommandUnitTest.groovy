package org.example.shared.resilience

import java.util.concurrent.Executors
import java.util.concurrent.Future
import org.example.shared.BaseUnitTest

/**
 * Unit test of the ExampleSemaphoreLoadShedderCommand object.
 */
class ExampleSemaphoreLoadShedderCommandUnitTest extends BaseUnitTest {

    def 'exercise too much load on the resource'() {
        List<Future<String>> futures = []
        def pool = Executors.newFixedThreadPool( 10 )

        given: 'a slow resource'
        def resource = new LatentResource( 750 )

        and: 'all available semaphores are used up'
        10.times {
            futures << pool.submit( new SemaphoreCommandInvocation( resource ) )
        }
        // pause to ensure all threads have started
        Thread.currentThread().sleep( 1000 * 2  )

        when: 'the command is executed'
        def sut = new ExampleSemaphoreLoadShedderCommand( resource )
        def results = sut.execute()

        then: 'the operation is denied and the fallback mechanism is invoked'
        'Fallback triggered!' == results

        and: 'all others succeed'
        futures.each {
            assert 'Success!' == it.get()
        }
    }
}
