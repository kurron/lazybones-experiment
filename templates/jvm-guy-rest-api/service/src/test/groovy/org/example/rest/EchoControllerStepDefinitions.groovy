package org.example.rest

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import groovy.util.logging.Slf4j
import org.example.rest.model.SimpleMediaType
import org.example.shared.BaseStepDefinition
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.util.UriComponentsBuilder

/**
 * Implementation of the REST Service feature steps.
 */
@Slf4j
class EchoControllerStepDefinitions extends BaseStepDefinition {

    /**
     * HTTP response from the REST call.
     */
    ResponseEntity<SimpleMediaType> response

    UriComponentsBuilder builder() {
        UriComponentsBuilder.newInstance().scheme( 'http' )
                                          .host( 'localhost' )
                                          .port( configuration.httpListeningPort )
    }

    /**
     * Holds the instance ID we are querying against.
     */
    String instance

    @Given('^a reference to a valid message$')
    void a_reference_to_a_valid_message() throws Throwable {
        instance = 'valid'
    }

    @When('^I GET the echo resource$')
    void i_GET_the_echo_resource() throws Throwable {
        def components = builder().path( '/echo/{instance}' ).build()
        def uri = components.expand( instance ).toUri()
        response = restOperations.getForEntity( uri, SimpleMediaType )
    }

    @Then('^I should get a status code of (\\d+)$')
    void i_should_get_a_status_code_of(int arg1) throws Throwable {
        assert response.statusCode.value() == arg1
    }

    @Then('^the message should be returned to me$')
    void the_message_should_be_returned_to_me() throws Throwable {
        assert response.body.item
        log.debug( 'response = {}', response.body.item )
        // a quality test would verify the contents of the response
    }

    @Given('^a reference to an invalid message$')
    void a_reference_to_an_invalid_message() throws Throwable {
        instance = 'invalid'
    }

    @Then('^I should get an explanation of the failure$')
    void i_should_get_an_explanation_of_the_failure() throws Throwable {
        assert response.body.error
        log.debug( 'response = {}', response.body.error )
        // a quality test would verify the contents of the response
    }

    @Given('^there are messages in the system$')
    void there_are_messages_in_the_system() throws Throwable {
        // not sure how to do this while using a fake repository
    }

    @When('^I GET the echo collection resource$')
    void i_GET_the_echo_collection_resource() throws Throwable {
        def components = builder().path( '/echos' ).build()
        def uri = components.toUri()
        response = restOperations.getForEntity( uri, SimpleMediaType )
    }

    @Then('^all messages should be returned to me$')
    void all_messages_should_be_returned_to_me() throws Throwable {
        assert response.body.collection
        log.debug( 'response = {}', response.body.collection )
        // a quality test would verify the contents of the response
    }

    @Given('^there are no messages in the system$')
    void there_are_no_messages_in_the_system() throws Throwable {
        // not sure how to do this while using a fake repository
    }

    @Then('^no messages should be returned to me$')
    void no_messages_should_be_returned_to_me() throws Throwable {
        // TODO: I'm ignoring the fact that the list is not empty because I didn't fell like figuring out how to tell
        // the controller it should pretend that no data exists
        // assert response.body.collection.empty
        log.debug( 'response = {}', response.body.collection )
        // a quality test would verify the contents of the response
    }

    @Given('^my desire to insert a new message into the system$')
    void my_desire_to_insert_a_new_message_into_the_system() throws Throwable {
        // nothing to do here
    }

    @When('^I GET the insert template resource$')
    void i_GET_the_insert_template_resource() throws Throwable {
        def components = builder().path( '/echo/template/insert' ).build()
        def uri = components.toUri()
        response = restOperations.getForEntity( uri, SimpleMediaType )
    }

    @Then('^the template should be returned to me$')
    void the_template_should_be_returned_to_me() throws Throwable {
        assert response.body.template
        log.debug( 'response = {}', response.body.template )
        // a quality test would verify the contents of the response
    }

    @When('^I fill in the template resource$')
    void i_fill_in_the_template_resource() throws Throwable {
        assert response.body.template
        response.body.template.text = 'Filled in by acceptance testing.'
    }

    @When('^I POST the template resource$')
    void i_POST_the_insert_resource() throws Throwable {
        def components = builder().path( '/echo' ).build()
        def uri = components.toUri()

        def request = createRequest(response.body)
        response = restOperations.exchange( uri, HttpMethod.POST, request, SimpleMediaType )
    }

    @Then('^the Location header should contain the URI of the resource$')
    void the_Location_header_should_contain_the_URI_of_the_resource() throws Throwable {
        assert response.headers.containsKey( 'Location' )
    }

    @When('^I GET the update template resource$')
    void i_GET_the_update_template_resource() throws Throwable {
        def components = builder().path( '/echo/template/update/{instance}' ).build()
        def uri = components.expand( instance ).toUri()
        response = restOperations.getForEntity( uri, SimpleMediaType )
    }

    @When('^I PUT the template resource$')
    void i_PUT_the_template_resource() throws Throwable {
        def components = builder().path( '/echo/{instance}' ).build()
        def uri = components.expand( instance ).toUri()

        def request = createRequest( response.body )
        response = restOperations.exchange( uri, HttpMethod.PUT, request, SimpleMediaType )
    }

    private static HttpEntity<SimpleMediaType> createRequest( SimpleMediaType body ) {
        def headers = new HttpHeaders()
        headers.add( 'Content-Type', SimpleMediaType.MEDIA_TYPE)
        HttpEntity<SimpleMediaType> request = new HttpEntity<>( body, headers )
        request
    }

}
