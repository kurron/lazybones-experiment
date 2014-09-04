package org.example.rest

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import groovy.transform.Canonical
import org.example.rest.model.SimpleMediaType
import org.example.shared.rest.ResourceNotFoundError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * An example of a hypermedia driven REST endpoint.
 */
@RestController
@RequestMapping( value = '/echo', consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE )
@Canonical
class EchoController {

    /**
     * Manages interaction with durable storage.
     */
    private final EchoRepository theRepository

    @Autowired
    EchoController( EchoRepository aRepository ) {
        theRepository = aRepository
    }

    @RequestMapping( value = '/{instance}', method = RequestMethod.GET )
    ResponseEntity<SimpleMediaType> fetchSpecificItem( @PathVariable String instance ) {
        // production would be more sophisticated -- this is just to make testing a bit easier to understand
        def found = theRepository.findOne( instance )
        if ( !found ) {
            throw new ResourceNotFoundError( instance,
                                             "The echo resource ${instance} is not in the system.",
                                             'The resource is not in the system.  Perhaps the identifier is incorrect?' )
        }
        new ResponseEntity<SimpleMediaType>( new SimpleMediaType( item: found ), HttpStatus.OK )
    }
}
