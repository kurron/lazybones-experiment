package org.example.rest

import org.example.echo.BytesToEchoRequestTransformer
import org.example.echo.EchoRequest
import org.example.rest.model.Item
import org.example.shared.BaseUnitTest
import org.example.shared.feedback.CustomFeedbackContext
import org.example.shared.rest.ResourceNotFoundError
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.integration.support.json.Jackson2JsonObjectMapper
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.web.servlet.DefaultMvcResult
import org.springframework.test.web.servlet.MvcResult
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
     * Test double for durable storage.
     */
    def stub = Stub( EchoRepository )

    /**
     * Subject Under Test.
     */
    def sut = MockMvcBuilders.standaloneSetup( new EchoController( stub ) ).defaultRequest( get( '/' ).accept( MediaType.APPLICATION_JSON ).contentType( MediaType.APPLICATION_JSON ) ).build()

    def 'verify unknown resource handling'() {

        given: 'stub trained to not to find the resource'
        stub.findOne( 'invalid' ) >> null

        when: 'a GET for an invalid instance is made'
        sut.perform( get( '/echo/invalid' ) ).andReturn()

        then: 'a resource not found error is thrown'
        def error = thrown( NestedServletException )
        error.cause instanceof ResourceNotFoundError
        error.cause.code == CustomFeedbackContext.RESOURCE_NOT_FOUND.code
    }

    def 'verify located resource handling'() {

        given: 'stub trained to find the resource'
        stub.findOne( 'invalid' ) >> new Item()

        when: 'a GET for a valid instance is made'
        MvcResult result = sut.perform( get( '/echo/valid' ) ).andReturn()

        then: 'the resource is found'
        result.response.status == HttpStatus.OK.value()
        def json = result.response.contentAsString
        json.contains( 'item' ) // can't do better testing because the Jackson marshaller isn't wired up in unit tests
    }
}
