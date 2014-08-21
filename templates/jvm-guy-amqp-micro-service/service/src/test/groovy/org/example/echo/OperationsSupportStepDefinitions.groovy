package org.example.echo

import cucumber.api.PendingException
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import groovy.util.logging.Slf4j
import org.example.shared.BaseStepDefinition
import org.springframework.http.ResponseEntity
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.util.UriComponentsBuilder

/**
 * Implementation of the Operations Support feature steps.
 */
@WebAppConfiguration
@Slf4j
class OperationsSupportStepDefinitions extends BaseStepDefinition {

    /**
     * HTTP response from the REST call.
     */
    ResponseEntity<String> response

    @Given('^a standing server$')
    void a_standing_server() throws Throwable {
        assert properties.httpListeningPort
    }

    @When('^I call the health check endpoint$')
    void i_call_the_health_check_endpoint() throws Throwable {
        def components = UriComponentsBuilder.newInstance().scheme( 'http' )
                                                           .host( 'localhost' )
                                                           .port( properties.httpListeningPort )
                                                           .path( '/health' )
                                                           .build()
        log.debug( 'URI = {}', components.toUriString() )
        response = restOperations.getForEntity( components.toUri(), String )
    }

    @Then('^I get a (\\d+) HTTP status code$')
    void i_get_a_HTTP_status_code(int arg1) throws Throwable {
        assert response.statusCode.value() == arg1
    }

    @Then('^detailed health information$')
    void detailed_health_information() throws Throwable {
        assert response.body
        //TODO: slurp the body and verify the JSON payload
    }

    @When('^I call the environment endpoint$')
    void i_call_the_environment_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @Then('^detailed environmental information$')
    void detailed_environmental_information() throws Throwable {
        assert response.body
        //TODO: slurp the body and verify the JSON payload
    }

    @When('^I call the info endpoint$')
    void i_call_the_info_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @Then('^detailed version information$')
    void detailed_version_information() throws Throwable {
        assert response.body
        //TODO: slurp the body and verify the JSON payload
    }
}
