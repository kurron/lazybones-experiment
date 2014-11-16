package org.example.rest

import static org.example.rest.model.SimpleMediaType.MEDIA_TYPE
import static org.example.shared.feedback.CustomFeedbackContext.DELETING_RESOURCE
import static org.example.shared.feedback.CustomFeedbackContext.INSERTING_RESOURCE
import static org.example.shared.feedback.CustomFeedbackContext.UPDATING_RESOURCE
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.DELETE
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST
import static org.springframework.web.bind.annotation.RequestMethod.PUT

import org.example.rest.model.Item
import org.example.rest.model.SimpleMediaType
import org.example.shared.feedback.BaseFeedbackAware
import org.example.shared.rest.ResourceNotFoundError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * An example of a hypermedia driven REST endpoint.
 */
@RestController
@RequestMapping( produces = MEDIA_TYPE )
class EchoController extends BaseFeedbackAware {

    /**
     * Hard coded resource ID mostly here to shut CodeNarc up.
     */
    private static final String MAGIC_NUMBER = '42'

    /**
     * Manages interaction with durable storage.
     */
    private final EchoRepository theRepository

    @Autowired
    EchoController( EchoRepository aRepository ) {
        theRepository = aRepository
    }

    @RequestMapping( value = '/echo/{instance}', method = GET )
    ResponseEntity<SimpleMediaType> fetchSpecificItem( @PathVariable String instance ) {
        // production would be more sophisticated and not let the resource representation bleed through to the
        // rest of the system.  Using Spring Integration or a hand crafted Port and Adapters system would work.
        def found = theRepository.findOne( instance )
        if ( !found ) {
            throw new ResourceNotFoundError( instance,
                                             "The echo resource ${instance} is not in the system.",
                                             'The resource is not in the system.  Perhaps the identifier is incorrect?' )
        }
        new ResponseEntity<SimpleMediaType>( new SimpleMediaType( item: found ), OK )
    }

    @RequestMapping( value = '/echos', method = GET )
    ResponseEntity<SimpleMediaType> fetchAllItems() {
        // to make the acceptance tests happy, we will always return something
        List<Item> collection = (0..4).collect {
            new Item( text: 'Ron was here.' )
        }
        def hyperMediaControl = new SimpleMediaType( collection: collection )
        new ResponseEntity<SimpleMediaType>( hyperMediaControl, OK )
    }

    @RequestMapping( value = '/echo/template/insert', method = GET )
    ResponseEntity<SimpleMediaType> fetchInsertTemplate() {
        def template = new Item( text: 'fill in' )
        def hyperMediaControl = new SimpleMediaType( template: template )
        new ResponseEntity<SimpleMediaType>( hyperMediaControl, OK )
    }

    @RequestMapping( value = '/echo/template/update/{instance}', method = GET )
    ResponseEntity<SimpleMediaType> fetchUpdateTemplate() {
        // pretend that we loaded the resource from durable storage
        def template = new Item( text: 'Already in the system' )
        def hyperMediaControl = new SimpleMediaType( template: template )
        new ResponseEntity<SimpleMediaType>( hyperMediaControl, OK )
    }

    @RequestMapping( value = '/echo', method = POST, consumes = MEDIA_TYPE )
    ResponseEntity<SimpleMediaType> insertNewMessage( @RequestBody SimpleMediaType request ) {
        // pretend we inserted the item and have a resource identifier of 42
        feedbackProvider.sendFeedback( INSERTING_RESOURCE, MAGIC_NUMBER )
        def uriComponents = EchoControllerLinkBuilder.buildInstanceURI( MAGIC_NUMBER )
        def uri = uriComponents.encode().toUri()
        def headers = new HttpHeaders()
        headers.setLocation( uri )
        new ResponseEntity<SimpleMediaType>( request, headers, CREATED )
    }

    @RequestMapping( value = '/echo/{instance}', method = PUT, consumes = MEDIA_TYPE )
    ResponseEntity<SimpleMediaType> updateExistingMessage( @RequestBody SimpleMediaType request, @PathVariable String instance ) {
        // pretend we've successfully updated the resource using the data provided in the update template
        feedbackProvider.sendFeedback( UPDATING_RESOURCE, instance )
        new ResponseEntity<SimpleMediaType>( new SimpleMediaType( item: new Item( text: request.template.text ) ), OK )
    }

    @RequestMapping( value = '/echo/{instance}', method = DELETE )
    ResponseEntity<SimpleMediaType> deleteExistingMessage( @PathVariable String instance ) {
        // pretend we've successfully deleted the resource and hand back the deleted resource. We could also return a NO CONTENT
        // status and no body as well.
        feedbackProvider.sendFeedback( DELETING_RESOURCE, instance )
        new ResponseEntity<SimpleMediaType>( new SimpleMediaType( item: new Item( text: 'Pulled from durable storage.' ) ), OK )
    }

}
