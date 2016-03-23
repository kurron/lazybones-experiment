package org.kurron.example.inbound

import org.junit.experimental.categories.Category
import org.kurron.categories.InboundIntegrationTest
import org.kurron.example.Application
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * An integration-level test of the SampleGateway object.
 **/
@Category( InboundIntegrationTest )
@WebIntegrationTest( randomPort = true )
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class RestGatewayIntegrationTest extends Specification implements GenerationAbility, RestCapable {

    @Value( '${local.server.port}' )
    int port

    private def template = new TestRestTemplate()

    def 'exercise GET happy path'() {
        given: 'a proper testing environment'
        assert port

        when: 'we GET /descriptor/application'
        def uri = buildURI( port, '/descriptor/application', [:] )
        def response = template.getForEntity( uri, String )

        then: 'we get a proper response'
        HttpStatus.OK == response.statusCode
    }
}
