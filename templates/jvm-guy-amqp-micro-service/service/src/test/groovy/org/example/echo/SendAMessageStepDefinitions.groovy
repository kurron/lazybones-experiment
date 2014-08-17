package org.example.echo

import cucumber.api.PendingException
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.example.shared.BaseStepDefinition

/**
 * Implementation of the upload message scenario.
 */
class SendAMessageStepDefinitions extends BaseStepDefinition {

    @Given( '^a text message I want to store$' )
    void a_text_message_I_want_to_store() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @When( '^I call the echo message service$' )
    void i_call_the_echo_message_service() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }

    @Then( '^my message should be stored in the system$' )
    void my_message_should_be_stored_in_the_system() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException()
    }
}
