/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback.exceptions;

import com.transparent.asgard.feedback.FeedbackContext;
import org.springframework.http.HttpStatus;

/**
 * Signals that a payload is too large and a 413 (payload too large) should be returned to the client.
 */
public class PayloadTooLargeError extends AbstractError
{
    public PayloadTooLargeError( final FeedbackContext context, final Object... arguments )
    {
        super( context, arguments );
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.PAYLOAD_TOO_LARGE;
    }

    @Override
    public String getDeveloperMessage()
    {
        return "The payload is too large, try sending something smaller next time";
    }
}
