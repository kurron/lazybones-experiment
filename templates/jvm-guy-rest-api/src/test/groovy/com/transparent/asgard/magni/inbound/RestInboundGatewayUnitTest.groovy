/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

import com.transparent.asgard.feedback.exceptions.LengthRequiredError
import com.transparent.asgard.feedback.exceptions.PayloadTooLargeError
import com.transparent.asgard.feedback.exceptions.PreconditionFailedError
import com.transparent.asgard.magni.ApplicationPropertiesBuilder
import com.transparent.asgard.magni.BaseUnitTest
import com.transparent.asgard.magni.feedback.MagniFeedbackContext
import com.transparent.asgard.magni.outbound.PersistenceOutboundGateway
import com.transparent.asgard.magni.outbound.RedisResourceBuilder
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.boot.actuate.metrics.GaugeService
import org.springframework.http.HttpStatus
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.NestedServletException

/**
 * Unit-level testing of the RestInboundGateway object.
 */
@SuppressWarnings( 'UnnecessaryGetter' )
class RestInboundGatewayUnitTest extends BaseUnitTest {

    def outboundGateway = Mock( PersistenceOutboundGateway )
    def configuration = new ApplicationPropertiesBuilder().build()
    def counter = Stub( CounterService )
    def gauge = Stub( GaugeService )
    def sut = new RestInboundGateway( outboundGateway, configuration, counter, gauge )
    def mapper = new Jackson2ObjectMapperBuilder().build()
    def mockMvc = MockMvcBuilders.standaloneSetup( sut ).build()
    def redisResource = new RedisResourceBuilder().build()

    def 'exercise asset storage'() {

        given: 'an expected resource id'
        def id = randomUUID()

        and: 'a number of minutes to wait until expiring the resource'
        def expirationMinutes = randomNumberExclusive( 10 )

        and: 'an expected resource uri'
        def expected = URI.create( "http://localhost/$id" )

        and: 'a valid request'
        def requestBuilder = MockMvcRequestBuilders.post( '/' )
                .content( redisResource.payload )
                .contentType( redisResource.contentType )
                .header( 'Content-Length', redisResource.payload.length )
                .header( CustomHttpHeaders.X_EXPIRATION_MINUTES, expirationMinutes )

        when: 'the POST request is made'
        def result = mockMvc.perform( requestBuilder ).andReturn()

        then: 'the outbound gateway is called'
        1 * outboundGateway.store( redisResource, expirationMinutes * 60 ) >> id

        and: 'a 201 (CREATED) status code is returned'
        result
        result.response.status == HttpStatus.CREATED.value()

        and: 'the response content type is set'
        result.response.contentType == MagniControl.MIME_TYPE

        and: 'the expected response is set in the body of the response'
        mapper.readValue( result.response.contentAsByteArray, MagniControl ).links.find { it.rel == 'self' }.href  == expected.toString()
    }

    def 'exercise asset retrieval request mappings'() {

        given: 'an id of an asset to retrieve'
        def id = randomUUID()

        and: 'a valid request'
        def requestBuilder = MockMvcRequestBuilders.get( '/{id}', id )

        when: 'the GET request is made'
        def result = mockMvc.perform( requestBuilder ).andReturn()

        then: 'the outbound gateway is called'
        1 * outboundGateway.retrieve( id ) >> redisResource

        then: 'a 200 (OK) status code is returned'
        result
        result.response.status == HttpStatus.OK.value()

        and: 'the response content type is set'
        result.response.contentType == redisResource.contentType

        and: 'the expected response is returned'
        result.response.contentAsByteArray == redisResource.payload
    }

    def 'exercise missing content length header'() {

        given: 'a request with no content length header set'
        def requestBuilder = MockMvcRequestBuilders.post( '/' ).content( redisResource.payload )

        when: 'the POST request is made'
        mockMvc.perform( requestBuilder ).andReturn()

        then: 'the expected error is thrown'
        def wrappedError = thrown( NestedServletException )
        def error = wrappedError.cause as LengthRequiredError
        error.code == MagniFeedbackContext.CONTENT_LENGTH_REQUIRED.code
    }

    def 'exercise max payload size exceeded'() {

        given: 'a small maximum payload size'
        configuration.maxPayloadSize = 1    // in MB

        and: 'a byte size that exceeds the maximum'
        def tooBig = randomByteArray( 1024 * 1024 + 1 ) // convert to MB and add 1 byte

        and: 'a request with a payload that exceeds the maximum allowed size'
        def requestBuilder = MockMvcRequestBuilders.post( '/' )
                .header( 'Content-Length', 128 )
                .content( tooBig )

        when: 'the POST request is made'
        mockMvc.perform( requestBuilder ).andReturn()

        then: 'the expected error is thrown'
        def wrappedError = thrown( NestedServletException )
        def error = wrappedError.cause as PayloadTooLargeError
        error.code == MagniFeedbackContext.PAYLOAD_TOO_LARGE.code
    }

    def 'exercise missing content type header'() {

        given: 'a request with no content type header set'
        def requestBuilder = MockMvcRequestBuilders.post( '/' )
                .header( 'Content-Length', 128 )
                .content( redisResource.payload )

        when: 'the POST request is made'
        mockMvc.perform( requestBuilder ).andReturn()

        then: 'the expected error is thrown'
        def wrappedError = thrown( NestedServletException )
        def error = wrappedError.cause as PreconditionFailedError
        error.code == MagniFeedbackContext.PRECONDITION_FAILED.code
        error.message.contains( 'Content-Type' )
    }

    def 'exercise missing expiration minutes header'() {

        given: 'a request with no content type header set'
        def requestBuilder = MockMvcRequestBuilders.post( '/' )
                .contentType( redisResource.contentType )
                .header( 'Content-Length', 128 )
                .content( redisResource.payload )

        when: 'the POST request is made'
        mockMvc.perform( requestBuilder ).andReturn()

        then: 'the expected error is thrown'
        def wrappedError = thrown( NestedServletException )
        def error = wrappedError.cause as PreconditionFailedError
        error.code == MagniFeedbackContext.PRECONDITION_FAILED.code
        error.message.contains( CustomHttpHeaders.X_EXPIRATION_MINUTES )
    }
}
