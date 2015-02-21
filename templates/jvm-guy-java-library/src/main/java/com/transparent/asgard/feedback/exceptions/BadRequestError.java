/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback.exceptions;

import com.transparent.asgard.feedback.FeedbackContext;
import org.springframework.http.HttpStatus;

/**
 * Signals that a invalid request was processed, and a 400 (bad request) should be returned to the client.
 */
public class BadRequestError extends AbstractError
{
    public BadRequestError( final FeedbackContext context, final Object... arguments )
    {
        super( context, arguments );
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getDeveloperMessage()
    {
        return "The server cannot or will not process the request due to something that is perceived to be a client error";
    }
}
