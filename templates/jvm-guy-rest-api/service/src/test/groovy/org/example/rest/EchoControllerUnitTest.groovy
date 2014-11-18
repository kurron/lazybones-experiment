package org.example.rest

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import org.example.rest.model.Item
import org.example.shared.BaseUnitTest
import org.example.shared.feedback.CustomFeedbackContext
import org.example.shared.rest.ResourceNotFoundError
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.NestedServletException

/**
 * Unit level test of the EchoController object.
 */
class EchoControllerUnitTest extends BaseUnitTest {

    /**
     * Test double for durable storage.
     */
    private final stub = Stub( EchoRepository )

    /**
     * Subject Under Test.
     */
    private final  sut = MockMvcBuilders.standaloneSetup( new EchoController( stub ) )
                                        .defaultRequest( get( '/' ).accept( APPLICATION_JSON ).contentType( APPLICATION_JSON ) )
                                        .build()

    def 'verify unknown resource handling'() {

        given: 'stub trained to not to find the resource'
        stub.findOne( 'invalid' ) >> null

        when: 'a GET for an invalid instance is made'
        MvcResult result = sut.perform( get( '/echo/invalid' ) ).andReturn()

        then: 'the error is reflected in the hyper-media control'
        result.response.status == HttpStatus.NOT_FOUND.value()
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
