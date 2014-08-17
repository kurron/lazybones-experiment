package org.example.echo

import cucumber.api.PendingException

/**
 * Implementation of the 'retrieve a message' scenario.
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

Given(~/^a previously echoed message$/) { ->
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException()
}

When(~/^I call the fetch message service$/) { ->
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException()
}

Then(~/^my message should be downloaded$/) { ->
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException()
}
