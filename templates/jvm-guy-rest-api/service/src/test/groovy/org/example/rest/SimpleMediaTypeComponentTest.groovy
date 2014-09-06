package org.example.rest

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import java.lang.reflect.Method
import org.example.rest.model.ErrorContext
import org.example.rest.model.Item
import org.example.rest.model.SimpleMediaType
import org.example.shared.BaseComponentTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.hateoas.Link
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Ignore

/**
 * A learning test to observe how the HATEOAS library enhances resources.
 */
@Slf4j
@WebAppConfiguration
@IntegrationTest( 'server.port = 0' )
@Ignore( 'The HATEOAS links are not working yet' )
class SimpleMediaTypeComponentTest extends BaseComponentTest {

    def 'learn about HATEOAS'() {

        given: 'valid JSON mapper'
        assert objectMapper

        when: 'I serialize a resource into JSON'
        def item = new Item( text: 'Hello' )
        def error = new ErrorContext( title: 'title', code: 'code', message: 'message' )
        def template = new Item( text: 'Please fill this in' )
        //def resource = new SimpleMediaType( item: item, error: error, template: template )
        def resource = new SimpleMediaType()
        resource.add( new Link( 'http://api.example.com/resource', Link.REL_SELF ) )
        resource.add( new Link( 'http://api.example.com/resource', Link.REL_FIRST ) )
        resource.add( new Link( 'http://api.example.com/resource', Link.REL_PREVIOUS ) )
        resource.add( new Link( 'http://api.example.com/resource', Link.REL_NEXT ) )
        resource.add( new Link( 'http://api.example.com/resource', Link.REL_LAST ) )
        resource.add( linkTo( methodOn( EchoController ).fetchSpecificItem( 'valid' ) ).withRel( 'valid' ) )

/*
        Method method = EchoController.getMethod( 'fetchSpecificItem', String )
        Link link = linkTo( method, 'instance' ).withRel( 'bob' )
        resource.add( link )
*/

        def json = objectMapper.writeValueAsString( resource )

        then: 'it looks as I expect'
        json
        log.debug( 'JSON = {}', json )
    }

}
