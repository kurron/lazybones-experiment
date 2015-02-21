/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback;

/**
 * Convenience base class for objects that wish to have a feedback provider injected into them.
 */
public class AbstractFeedbackAware implements FeedbackAware
{
    /**
     * The provider to use.
     */
    private FeedbackProvider theFeedbackProvider;

    protected AbstractFeedbackAware() {
        theFeedbackProvider = new NullFeedbackProvider();
    }

    @Override
    public FeedbackProvider getFeedbackProvider()
    {
        return theFeedbackProvider;
    }

    @Override
    public void setFeedbackProvider( final FeedbackProvider aProvider )
    {
        theFeedbackProvider = aProvider;
    }
}
