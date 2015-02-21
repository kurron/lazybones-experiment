/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

import com.transparent.asgard.magni.BaseIntegrationTest
import com.transparent.asgard.magni.feedback.MagniFeedbackContext
import groovy.util.logging.Slf4j
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity

/**
 * Integration-level testing of the RestInboundGateway object.
 */
@SuppressWarnings( 'UnnecessaryGetter' )
@Slf4j
class RestInboundGatewayIntegrationTest extends BaseIntegrationTest {

    def 'exercise asset storage and retrieval'() {

        given: 'a valid payload to store'
        def payload = randomByteArray( 128 )

        and: 'required headers are set'
        def headers = buildHeaders()
        headers.setContentType( MediaType.IMAGE_GIF )
        headers.set( CustomHttpHeaders.X_EXPIRATION_MINUTES, 5.toString() )
        def requestEntity = new HttpEntity( payload, headers )

        when: 'the payload is stored'
        def responseEntity = restOperations.postForEntity( serverUri, requestEntity, MagniControl )

        then: 'a 201 (CREATED) status is returned'
        responseEntity.statusCode == HttpStatus.CREATED

        and: 'the expected URI is returned in the location header'
        def location = responseEntity.headers.getLocation()
        location.toString().contains( serverUri.toString() )

        and: 'the URI is also returned in the body of the response'
        responseEntity.body.links.find { it.rel == 'self' }.href == location.toString()

        and: 'the asset can be retrieved'
        def entity = new RequestEntity( buildHeaders(), HttpMethod.GET, location )
        def response = restOperations.exchange( entity, byte[] )
        response.statusCode == HttpStatus.OK
        response.body == payload

        and: 'the content-type header is set'
        response.headers.getContentType().includes(  MediaType.IMAGE_GIF )
    }

    def 'exercise not found asset'() {

        given: 'a request entity with properly set headers'
        def headers = buildHeaders()
        // accept both JSON (our error document) and stream (expected bytes)
        headers.setAccept( [MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM] )
        def requestEntity = new RequestEntity( headers, HttpMethod.GET, URI.create( "$serverUri${UUID.randomUUID()}" ) )

        when: 'a non-existent resource is retrieved'
        def result = restOperations.exchange( requestEntity, byte[] )

        then: 'a 404 (not found) is returned'
        result.statusCode == HttpStatus.NOT_FOUND

        and: 'a json fault document is returned in the body of the response'
        result.headers.getContentType().includes( MediaType.APPLICATION_JSON )
        def control = mapper.readValue( result.body, MagniControl )
        control.httpCode == HttpStatus.NOT_FOUND.value()
        control.errorBlock.code == MagniFeedbackContext.REDIS_RESOURCE_NOT_FOUND.code
        log.info 'Fault message: {}', control.errorBlock.message
        log.info 'Developer message: {}', control.errorBlock.developerMessage
    }

    def 'exercise missing correlation id'() {

        given: 'a request entity with a missing correlation id header'
        // accept both JSON (our error document) and stream (expected bytes)
        def headers = new HttpHeaders( accept: [MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM] )
        def requestEntity = new RequestEntity( headers, HttpMethod.GET, URI.create( "$serverUri${UUID.randomUUID()}" ) )

        when: 'a request is made'
        def result = restOperations.exchange( requestEntity, byte[] )

        then: 'a 412 (precondition failed) is returned'
        result.statusCode == HttpStatus.PRECONDITION_FAILED

        and: 'a json fault document is returned in the body of the response'
        result.headers.getContentType().includes( MediaType.APPLICATION_JSON )
        def control = mapper.readValue( result.body, MagniControl )
        control.httpCode == HttpStatus.PRECONDITION_FAILED.value()
        control.errorBlock.code == MagniFeedbackContext.PRECONDITION_FAILED.code
        log.info 'Fault message: {}', control.errorBlock.message
        log.info 'Developer message: {}', control.errorBlock.developerMessage
    }

    def 'exercise home resource'() {

        given: 'a request entity with properly set headers'
        def headers = buildHeaders()
        headers.setAccept( [MagniControl.MEDIA_TYPE] )
        def requestEntity = new RequestEntity( headers, HttpMethod.GET, serverUri )

        when: 'the  resource is retrieved'
        def result = restOperations.exchange( requestEntity, MagniControl )

        then: 'a 200 (OK) is returned'
        result.statusCode == HttpStatus.OK

        and: 'a control is returned in the body of the response'
        result.headers.getContentType().includes( MagniControl.MEDIA_TYPE )
        MagniControl control = result.body
        control.httpCode == HttpStatus.OK.value()
        control.links.find { it.rel == RestInboundGateway.API_DISCOVERY_RELATION }
        control.links.find { it.rel == RestInboundGateway.UPLOAD_RELATION }
    }

    private HttpHeaders buildHeaders() {
        def headers = new HttpHeaders()
        headers.set( CustomHttpHeaders.X_CORRELATION_ID, randomHexString() )
        headers
    }
}
