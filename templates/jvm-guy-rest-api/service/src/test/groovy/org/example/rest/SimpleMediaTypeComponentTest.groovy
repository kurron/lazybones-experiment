package org.example.rest

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.example.rest.model.ErrorContext
import org.example.rest.model.Item
import org.example.rest.model.SimpleMediaType
import org.example.shared.BaseComponentTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.Link

/**
 * A learning test to observe how the HATEOAS library enhances resources.
 */
@Slf4j
class SimpleMediaTypeComponentTest extends BaseComponentTest {

    @Autowired
    ObjectMapper objectMapper

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
        def json = objectMapper.writeValueAsString( resource )

        then: 'it looks as I expect'
        json
        log.debug( 'JSON = {}', json )
    }

}
