/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback.exceptions;

import com.transparent.asgard.feedback.FeedbackContext;
import org.springframework.http.HttpStatus;

/**
 * Signals that a resource was not found, and a 404 (not found) HTTP code should be returned to the client.
 */
public class NotFoundError extends AbstractError
{
    public NotFoundError( final FeedbackContext context, final Object... arguments )
    {
        super( context, arguments );
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getDeveloperMessage()
    {
        return "The resource does not exist in the system";
    }
}
