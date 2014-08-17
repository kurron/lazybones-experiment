package org.example.echo

import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * User acceptance test for the echo service.
 */
@RunWith( Cucumber )
@Cucumber.Options( tags = ['@api'],
                   format = ['pretty', 'html:target/cucumber'],
                   features = ['src/test/resources'] )
class EchoServiceAcceptanceTest {
}
