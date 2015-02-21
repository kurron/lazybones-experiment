/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.exception

import com.transparent.asgard.feedback.exceptions.AbstractError
import com.transparent.asgard.feedback.exceptions.LengthRequiredError
import com.transparent.asgard.feedback.exceptions.NotFoundError
import com.transparent.asgard.feedback.exceptions.PayloadTooLargeError
import com.transparent.asgard.feedback.exceptions.PreconditionFailedError
import com.transparent.asgard.magni.BaseUnitTest
import com.transparent.asgard.magni.feedback.MagniFeedbackContext
import com.transparent.asgard.magni.inbound.MagniControl
import org.springframework.http.HttpStatus

/**
 * Unit-level testing of the GlobalExceptionHandler object.
 */
@SuppressWarnings( 'UnnecessaryGetter' )
class GlobalExceptionHandlerUnitTest extends BaseUnitTest {

    def sut = new GlobalExceptionHandler()

    def 'exercise error handling'( Class clazz ) {

        given: 'a valid magni exception'
        def feedback = randomEnum( MagniFeedbackContext )
        def error = clazz.newInstance( feedback ) as AbstractError

        when: 'an exception is handled'
        def result = sut.handleMagniException( error )

        then: 'a valid http response is returned'
        result
        result.statusCode == error.httpStatus
        result.headers.getContentType() == MagniControl.MEDIA_TYPE

        and: 'the body contains the expected fault document'
        def control = result.body
        control
        control.httpCode == error.httpStatus.value()
        control.errorBlock.code == feedback.code
        control.errorBlock.message == error.message
        control.errorBlock.developerMessage == error.developerMessage

        where: 'each error type is provided'
        clazz << [NotFoundError, LengthRequiredError, PayloadTooLargeError, PreconditionFailedError]
    }

    def 'exercise non-magni error handling'() {

        given: 'a non-magni error'
        def error = new RuntimeException( 'expected to fail' )

        when: 'an error is handled'
        def result = sut.handleException( error, null )

        then: 'a valid http response is returned'
        result
        result.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        result.headers.getContentType() == MagniControl.MEDIA_TYPE

        and: 'the body contains a valid fault document'
        def control = result.body as MagniControl
        control
        control.httpCode == result.statusCode.value()
        control.errorBlock.code == MagniFeedbackContext.GENERIC_ERROR.code
    }
}
