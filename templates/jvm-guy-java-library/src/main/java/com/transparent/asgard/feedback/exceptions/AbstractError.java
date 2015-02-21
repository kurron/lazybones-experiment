/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback.exceptions;

import com.transparent.asgard.feedback.FeedbackContext;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;

/**
 * A common base class for all Magni errors.
 */
public abstract class AbstractError extends RuntimeException
{
    /**
     * Unique error code.
     */
    private final int code;

    protected AbstractError( final FeedbackContext context, final Object... arguments )
    {
        super( MessageFormatter.arrayFormat( context.getFormatString(), arguments ).getMessage() );
        code = context.getCode();
    }

    public int getCode()
    {
        return code;
    }

    /**
     * Returns the HTTP status associated with this error.
     * @return the HTTP status.
     */
    public abstract HttpStatus getHttpStatus();

    /**
     * Returns a more informative error message, geared toward developers.
     * @return the developer message.
     */
    public abstract String getDeveloperMessage();
}
