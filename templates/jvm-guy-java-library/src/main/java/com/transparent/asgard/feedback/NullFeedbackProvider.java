/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback;

/**
 * Null Object Pattern: a no-op implementation of the feedback provider interface. Typically used only in testing
 * environments where real providers are not desired or a fallback is needed.
 */
public class NullFeedbackProvider implements FeedbackProvider
{
    @Override
    public void sendFeedback( final FeedbackContext context, final Object... arguments )
    {
        // do nothing
    }

    @Override
    public void sendFeedback( final FeedbackContext context, final Throwable error )
    {
        // do nothing
    }
}
