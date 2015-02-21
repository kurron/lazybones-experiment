/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.exception

import static com.transparent.asgard.magni.feedback.MagniFeedbackContext.GENERIC_ERROR
import com.transparent.asgard.feedback.FeedbackAware
import com.transparent.asgard.feedback.FeedbackProvider
import com.transparent.asgard.feedback.NullFeedbackProvider
import com.transparent.asgard.feedback.exceptions.AbstractError
import com.transparent.asgard.magni.inbound.ErrorBlock
import com.transparent.asgard.magni.inbound.MagniControl
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Global handling for REST exceptions.
 */
@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements FeedbackAware {

    /**
     * The provider to use.
     */
    @Delegate
    private FeedbackProvider theFeedbackProvider = new NullFeedbackProvider()

    @Override
    FeedbackProvider getFeedbackProvider() {
        theFeedbackProvider
    }

    @Override
    void setFeedbackProvider( final FeedbackProvider aProvider ) {
        theFeedbackProvider = aProvider
    }

    @Override
    protected ResponseEntity<MagniControl> handleExceptionInternal( Exception e,
                                                                    Object body,
                                                                    HttpHeaders headers,
                                                                    HttpStatus status,
                                                                    WebRequest request ) {
        sendFeedback( GENERIC_ERROR, e.message )
        def control = new MagniControl( httpCode: status.value() )
        control.errorBlock = new ErrorBlock( code: GENERIC_ERROR.code,
                                             message: e.message,
                                             developerMessage: 'Indicates that the exception was not handled explicitly and is being handled as a generic error' )
        wrapInResponseEntity( control, status, headers )
    }

    /**
     * Handles errors thrown by Magni itself.
     * @param e the error.
     * @return the constructed response entity, containing details about the error.
     */
    @ExceptionHandler( AbstractError )
    static ResponseEntity<MagniControl> handleMagniException( AbstractError e ) {
        def control = new MagniControl( httpCode: e.httpStatus.value() ).with {
            errorBlock = new ErrorBlock( code: e.code, message: e.message, developerMessage: e.developerMessage )
            it
        }
        wrapInResponseEntity( control, e.httpStatus )
    }

    /**
     * Wraps the provided control in a response entity.
     * @param control the control to return in the body of the response.
     * @param status the HTTP status to return.
     * @param headers the HTTP headers to return. If provided, the existing headers are used, otherwise new headers are created.
     * @return the response entity.
     */
    private static ResponseEntity<MagniControl> wrapInResponseEntity( MagniControl control,
                                                                      HttpStatus status,
                                                                      HttpHeaders headers = new HttpHeaders() ) {
        headers.setContentType( MagniControl.MEDIA_TYPE )
        new ResponseEntity<>( control, headers, status )
    }
}