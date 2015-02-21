/*
 * Copyright (c) 2015. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.transparent.asgard.magni.exception

import org.kurron.feedback.exceptions.AbstractError
import org.kurron.feedback.exceptions.LengthRequiredError
import org.kurron.feedback.exceptions.NotFoundError
import org.kurron.feedback.exceptions.PayloadTooLargeError
import org.kurron.feedback.exceptions.PreconditionFailedError
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
