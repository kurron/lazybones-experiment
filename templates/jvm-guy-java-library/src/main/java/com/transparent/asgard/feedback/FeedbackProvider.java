/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback;

/**
 * Contract for objects that know how to send feedback via various mechanisms, such as logging.
 */
public interface FeedbackProvider
{
    /**
     * Places a feedback message into the system.
     * @param context which message we want to send.
     * @param arguments any information that should be inserted into the formatted message.
     */
    void sendFeedback( final FeedbackContext context, final Object... arguments );

    /**
     * Places a feedback message into the system.
     * @param context which message we want to send.
     * @param error the causal error you want to embed in the feedback.
     */
    void sendFeedback( final FeedbackContext context, final Throwable error );
}
