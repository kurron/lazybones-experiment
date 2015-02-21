/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni

import com.transparent.asgard.feedback.exceptions.PreconditionFailedError
import com.transparent.asgard.magni.feedback.MagniFeedbackContext
import com.transparent.asgard.magni.inbound.CustomHttpHeaders
import org.slf4j.MDC
import org.springframework.mock.web.MockHttpServletRequest

/**
 * Unit-level testing of the CorrelationIdHandlerInterceptor object.
 */
class CorrelationIdHandlerInterceptorUnitTest extends BaseUnitTest {

    def configuration = new ApplicationPropertiesBuilder().build()
    def sut = new CorrelationIdHandlerInterceptor( configuration )

    def 'exercise correlation id extraction'() {

        given: 'a correlation id'
        def correlationId = randomHexString()

        and: 'a valid http request'
        def request = new MockHttpServletRequest()
        request.addHeader( CustomHttpHeaders.X_CORRELATION_ID, correlationId )

        when: 'pre handle is called'
        def result = sut.preHandle( request, null, null )

        then: 'the correlation id is set in the thread local MDC'
        MDC.get( CorrelationIdHandlerInterceptor.CORRELATION_ID ) == correlationId

        and: 'true is returned'
        result
    }

    def 'exercise missing header error handling'() {

        given: 'a valid http request with no correlation id'
        def request = new MockHttpServletRequest()

        and: 'the correlation id is required'
        configuration.requireCorrelationId = true

        when: 'pre handle is called'
        sut.preHandle( request, null, null )

        then: 'a missing header error is thrown'
        def error = thrown( PreconditionFailedError )
        error.code == MagniFeedbackContext.PRECONDITION_FAILED.code
    }

    def 'exercise missing header when it is not required'() {

        given: 'a valid http request with no correlation id'
        def request = new MockHttpServletRequest()

        and: 'the correlation id is not required'
        configuration.requireCorrelationId = false

        when: 'pre handle is called'
        sut.preHandle( request, null, null )

        then: 'a missing header error is not thrown'
        notThrown( PreconditionFailedError )

        and: 'a correlation id is generated'
        def generated = MDC.get( CorrelationIdHandlerInterceptor.CORRELATION_ID )
        generated
        // make sure it's a UUID string
        generated == UUID.fromString( generated ).toString()

    }
}
