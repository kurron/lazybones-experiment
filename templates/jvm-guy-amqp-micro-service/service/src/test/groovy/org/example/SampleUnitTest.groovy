package org.example

import groovy.util.logging.Slf4j
import org.example.shared.BaseUnitTest

/**
 * Simple example of a Spock unit test.
 */
@Slf4j
class SampleUnitTest extends BaseUnitTest {

    /**
     * Subject under test.
     */
    private final List<String> sut = []

    def 'verify addition operator'() {
        given: 'an empty list'
        assert sut.empty

        when: 'an item is added via the << operator'
        def added = randomHexString()
        sut << added

        then: 'sut contains the new string'
        sut.find { it == added }
        log.debug( 'end of test' )
    }
}
