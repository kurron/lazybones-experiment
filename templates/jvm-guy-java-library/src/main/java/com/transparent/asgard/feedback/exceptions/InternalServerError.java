/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback.exceptions;

import com.transparent.asgard.feedback.FeedbackContext;
import org.springframework.http.HttpStatus;

/**
 * Signals that a generic server error occurred, and a 500 (internal server error) should be returned to the client.
 */
public class InternalServerError extends AbstractError
{
    public InternalServerError( final FeedbackContext context, final Object... arguments )
    {
        super( context, arguments );
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getDeveloperMessage()
    {
        return "Something bad happened...";
    }
}
