/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback.exceptions;

import com.transparent.asgard.feedback.FeedbackContext;
import org.springframework.http.HttpStatus;

/**
 * Signals that a precondition was not met (typically a missing header that is required), and a 412 (precondition failed) should be
 * returned to the client.
 */
public class PreconditionFailedError extends AbstractError
{
    public PreconditionFailedError( final FeedbackContext context, final Object... arguments )
    {
        super( context, arguments );
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.PRECONDITION_FAILED;
    }

    @Override
    public String getDeveloperMessage()
    {
        return "Did you forget to set a required header?";
    }
}
