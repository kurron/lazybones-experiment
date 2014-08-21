package org.example.shared.resilience

import java.util.concurrent.Future
import org.example.shared.BaseUnitTest

/**
 * Unit test of the ExampleThreadPoolLoadShedderCommand object.
 */
class ExampleThreadPoolLoadShedderCommandUnitTest extends BaseUnitTest {

    def 'exercise too much load on the resource'() {
        List<Future<String>> futures = []

        given: 'a slow resource'
        def resource = new LatentResource( 2000 )

        and: 'all available threads are used up'
        10.times {
            futures <<  new ExampleThreadPoolLoadShedderCommand( resource ).queue()
        }

        when: 'the command is executed'
        def sut = new ExampleThreadPoolLoadShedderCommand( resource )
        def results = sut.execute()

        then: 'the operation is denied and the fallback mechanism is invoked'
        'Fallback triggered!' == results
    }
}
