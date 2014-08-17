package org.example.echo

import cucumber.api.PendingException
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.example.shared.BaseStepDefinition

/**
 * Implementation of the retrieve message scenario.
 */
class RetrieveAMessageStepDefinitions extends BaseStepDefinition {

    @Given( '^a previously echoed message$' )
    void a_previously_echoed_message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @When( '^I call the fetch message service$' )
    void i_call_the_fetch_message_service() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @Then( '^my message should be downloaded$' )
    void my_message_should_be_downloaded() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }
}
