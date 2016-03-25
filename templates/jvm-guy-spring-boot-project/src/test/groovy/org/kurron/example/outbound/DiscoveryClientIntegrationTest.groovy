package org.kurron.example.outbound

import org.junit.experimental.categories.Category
import org.kurron.categories.OutboundIntegrationTest
import org.kurron.example.Application
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

/**
 * An integration-level test of the service discovery mechanisms object.
 **/
@Category( OutboundIntegrationTest )
@IntegrationTest
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class DiscoveryClientIntegrationTest extends Specification implements GenerationAbility {

    @Value( '${spring.application.name}' )
    String serviceName

    @Autowired
    private DiscoveryClient discoveryClient

    def 'call service by hand'() {
        given: 'a proper testing environment'
        assert serviceName
        assert discoveryClient

        when: 'we call checkTheTime'
        def template = new TestRestTemplate()
        def uri = constructURI( '/descriptor/application' )
        ResponseEntity<String> response = template.getForEntity( uri, String )

        then: 'we get a proper response'
        response.statusCode == HttpStatus.OK
    }

    URI constructURI( String path ) {
        def instances = discoveryClient.getInstances( serviceName )
        //TODO: a robust solution can deal with no available instances
        def chosen = randomElement( instances ) as ServiceInstance
        UriComponentsBuilder.newInstance()
                            .scheme( 'http' )
                            .host( chosen.host )
                            .port( chosen.port )
                            .path( path )
                            .build().toUri()
    }
}
