package org.example.rest

import cucumber.api.PendingException
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import groovy.util.logging.Slf4j
import org.example.shared.BaseStepDefinition
import org.springframework.http.ResponseEntity
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.util.UriComponentsBuilder

/**
 * Implementation of the REST Service feature steps.
 */
@WebAppConfiguration
@Slf4j
class EchoControllerStepDefinitions extends BaseStepDefinition {

    /**
     * HTTP response from the REST call.
     */
    ResponseEntity<String> response

    /**
     * Simplifies the creation of URIs.
     */
    UriComponentsBuilder builder

    @Before
    void setup() {
        builder = UriComponentsBuilder.newInstance().scheme( 'http' )
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
        def components = builder.path( '/echo/{instance}' ).build()
        response = restOperations.getForEntity( components.expand( instance ).toUri(), String )
    }

    @Then('^I should get a status code of (\\d+)$')
    void i_should_get_a_status_code_of(int arg1) throws Throwable {
        assert response.statusCode.value() == arg1
    }

    @Then('^the message should be returned to me$')
    void the_message_should_be_returned_to_me() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given('^a reference to an invalid message$')
    void a_reference_to_an_invalid_message() throws Throwable {
        instance = 'invalid'
    }

    @Then('^I should get an explanation of the failure$')
    void i_should_get_an_explanation_of_the_failure() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
