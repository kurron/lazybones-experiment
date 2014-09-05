package org.example.rest

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import org.example.rest.model.Item
import org.example.rest.model.SimpleMediaType
import org.example.shared.rest.ResourceNotFoundError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder

/**
 * An example of a hypermedia driven REST endpoint.
 */
@RestController
@RequestMapping( produces = APPLICATION_JSON_VALUE )
class EchoController {

    /**
     * Manages interaction with durable storage.
     */
    private final EchoRepository theRepository

    @Autowired
    EchoController( EchoRepository aRepository ) {
        theRepository = aRepository
    }

    @RequestMapping( value = '/echo/{instance}', method = RequestMethod.GET )
    ResponseEntity<SimpleMediaType> fetchSpecificItem( @PathVariable String instance ) {
        // production would be more sophisticated and not let the resource representation bleed through to the
        // rest of the system.  Using Spring Integration or a hand crafted Port and Adapters system would work.
        def found = theRepository.findOne( instance )
        if ( !found ) {
            throw new ResourceNotFoundError( instance,
                                             "The echo resource ${instance} is not in the system.",
                                             'The resource is not in the system.  Perhaps the identifier is incorrect?' )
        }
        new ResponseEntity<SimpleMediaType>( new SimpleMediaType( item: found ), HttpStatus.OK )
    }

    @RequestMapping( value = '/echos', method = RequestMethod.GET )
    ResponseEntity<SimpleMediaType> fetchAllItems() {
        // to make the acceptance tests happy, we will always return something
        List<Item> collection = (0..4).collect {
            new Item( text: 'Ron was here.' )
        }
        def hyperMediaControl = new SimpleMediaType( collection: collection )
        new ResponseEntity<SimpleMediaType>( hyperMediaControl, HttpStatus.OK )
    }

    @RequestMapping( value = '/echo/template/insert', method = RequestMethod.GET )
    ResponseEntity<SimpleMediaType> fetchInsertTemplate() {
        def template = new Item( text: 'fill in' )
        def hyperMediaControl = new SimpleMediaType( template: template )
        new ResponseEntity<SimpleMediaType>( hyperMediaControl, HttpStatus.OK )
    }

    @RequestMapping( value = '/echo', method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE )
    ResponseEntity<SimpleMediaType> insertNewMessage( SimpleMediaType request ) {
        // pretend we inserted the item and have a resource identifier of 42
        def uriComponents = MvcUriComponentsBuilder.fromMethodName( EchoController, 'fetchSpecificItem', 'instance' ).buildAndExpand( '42' )
        def uri = uriComponents.encode().toUriString()
        def headers = new HttpHeaders()
        headers.add( 'Location', uri )
        new ResponseEntity<SimpleMediaType>( request, headers, HttpStatus.CREATED )
    }
}
