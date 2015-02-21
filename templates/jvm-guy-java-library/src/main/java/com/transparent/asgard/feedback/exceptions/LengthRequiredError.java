/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback.exceptions;

import com.transparent.asgard.feedback.FeedbackContext;
import org.springframework.http.HttpStatus;

/**
 * Signals that a required Content-Length header was not set and a status code of 411 (length required) should be
 * returned to the client.
 */
public class LengthRequiredError extends AbstractError
{
    public LengthRequiredError( final FeedbackContext context, final Object... arguments )
    {
        super( context, arguments );
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.LENGTH_REQUIRED;
    }

    @Override
    public String getDeveloperMessage()
    {
        return "It looks like you forgot to set the Content-Length header";
    }
}
