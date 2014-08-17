package org.example.echo

import cucumber.api.PendingException

/**
 * Implementation of the 'retrieve a message' scenario.
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Given(~/^a text message I want to store$/) { ->
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException()
}

When(~/^I call the echo message service$/) { ->
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException()
}

Then(~/^my message should be stored in the system$/) { ->
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException()
}
