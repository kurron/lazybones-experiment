package org.example

import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Driver for user acceptance tests.
 */
@Cucumber.Options( tags = ['@api'],
                   format = ['pretty', 'html:target/cucumber'],
                   features = ['src/test/resources'] )
@RunWith( Cucumber )
class ApiAcceptanceTest {
}
