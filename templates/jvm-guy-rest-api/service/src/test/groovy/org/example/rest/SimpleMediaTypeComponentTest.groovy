package org.example.rest

import org.junit.Before
import org.springframework.http.HttpEntity
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

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
//@Ignore( 'The HATEOAS links are not working yet' )
class SimpleMediaTypeComponentTest extends BaseComponentTest {

    def 'learn about HATEOAS'() {

        given: 'valid JSON mapper'
        assert objectMapper

        and: 'valid mock servlet environment'
        def request = new MockHttpServletRequest();
        def requestAttributes = new ServletRequestAttributes( request );
        RequestContextHolder.setRequestAttributes( requestAttributes );

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
        resource.add( linkTo( EchoController ).withRel( 'to-echo-controller' ) )
// Bad news: the look up code does NOT work with Groovy.  You get a stack overflow error.  Java works fine.
//        resource.add( linkTo( methodOn( EchoController ).fetchSpecificItem( 'valid' ) ).withRel( 'valid' ) )

/*
        Method method = EchoController.getMethod( 'fetchSpecificItem', String )
        Link link = linkTo( method, 'instance' ).withRel( 'buildInstanceURI' )
        resource.add( link )
*/

        def json = objectMapper.toJson( resource )

        then: 'it looks as I expect'
        json
        log.debug( 'JSON = {}', json )
    }
}
