package org.kurron.example.outbound

import org.junit.experimental.categories.Category
import org.kurron.categories.OutboundIntegrationTest
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * An integration-level test of the Feign HTTP client.
 **/
@Category( OutboundIntegrationTest )
@IntegrationTest
@ContextConfiguration( classes = FeignIntegrationTestConfiguration, loader = SpringApplicationContextLoader )
class FeignIntegrationTest extends Specification implements GenerationAbility {

    @Autowired
    private RestGatewayClient client

    def 'call happy path'() {
        given: 'a proper testing environment'
        assert client

        when: 'we call happyPath'
        def results = client.happyPath()

        then: 'we get a proper response'
        results
        println results
    }

    def 'call system systemFailure'() {
        given: 'a proper testing environment'
        assert client

        when: 'we call systemFailure'
        def results = client.systemFailure()

        then: 'we get a proper response'
        results
        println results
    }

    def 'call system applicationFailure'() {
        given: 'a proper testing environment'
        assert client

        when: 'we call systemFailure'
        def results = client.applicationFailure()

        then: 'we get a proper response'
        results
        println results
    }
}
