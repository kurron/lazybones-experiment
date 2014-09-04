package org.example.rest

import groovy.transform.Canonical
import org.example.rest.model.SimpleMediaType
import org.example.shared.rest.ResourceNotFoundError
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * An example of a hypermedia driven REST endpoint.
 */
@RestController
@RequestMapping( value = '/echo', consumes = 'application/json', produces = 'application/json' )
@Canonical
class EchoController {

    @RequestMapping( value = '/{instance}', method = RequestMethod.GET )
    ResponseEntity<SimpleMediaType> fetchSpecificItem( @PathVariable String instance ) {
        // TODO: fetch the resource via its instance identifier
        throw new ResourceNotFoundError( instance,
                                             "The echo resource ${instance} is not in the system.",
                                             'The resource is not in the system.  Perhaps the identifier is incorrect?' )
    }
}
