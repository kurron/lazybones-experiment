package org.example.echo

import cucumber.api.PendingException
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.example.shared.BaseStepDefinition

/**
 * Implementation of the Operations Support feature steps.
 */
class OperationsSupportStepDefinitions extends BaseStepDefinition {

    @Given('^a standing server$')
    void a_standing_server() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @When('^I call the health check endpoint$')
    void i_call_the_health_check_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @Then('^I get a (\\d+) HTTP status code$')
    void i_get_a_HTTP_status_code(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assert 200 == arg1
        throw new PendingException()
    }

    @Then('^detailed health information$')
    void detailed_health_information() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @When('^I call the environment endpoint$')
    void i_call_the_environment_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @Then('^detailed environmental information$')
    void detailed_environmental_information() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @When('^I call the info endpoint$')
    void i_call_the_info_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @Then('^detailed version information$')
    void detailed_version_information() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }
}
