package org.example.rest

import org.example.echo.BytesToEchoRequestTransformer
import org.example.echo.EchoRequest
import org.example.shared.BaseUnitTest
import org.example.shared.feedback.CustomFeedbackContext
import org.example.shared.rest.ResourceNotFoundError
import org.springframework.http.MediaType
import org.springframework.integration.support.json.Jackson2JsonObjectMapper
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.web.servlet.DefaultMvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.NestedServletException

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
/**
 * Unit level test of the EchoController object.
 */
class EchoControllerUnitTest extends BaseUnitTest {

    /**
     * Subject Under Test.
     */
    def sut = MockMvcBuilders.standaloneSetup( new EchoController() ).defaultRequest( get( '/' ).accept( MediaType.APPLICATION_JSON ).contentType( MediaType.APPLICATION_JSON ) ).build()

    def 'verify unknown resource handling'() {

        when: 'a GET for an invalid instance is made'
        sut.perform( get( '/echo/invalid' ) ).andReturn()

        then: 'a resource not found error is thrown'
        def error = thrown( NestedServletException )
        error.cause instanceof ResourceNotFoundError
        error.cause.code == CustomFeedbackContext.RESOURCE_NOT_FOUND.code
    }
}
