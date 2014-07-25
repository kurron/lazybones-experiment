package org.example

/**
 * Created by vagrant on 7/24/14.
 */
class SampleUnitTest extends BaseSpecification {

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
