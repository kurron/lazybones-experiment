package org.example

/**
 * Simple example of a Spock unit test.
 */
class SampleUnitTest extends BaseUnitTest {

    /**
     * Subject under test.
     */
    List<String> sut = []

    def 'verify addition operator'() {
        given: 'an empty list'
        assert sut.empty

        when: 'an item is added via the << operator'
        def added = randomHexString()
        sut << added

        then: 'sut contains the new string'
        sut.find { it == added }
    }
}
