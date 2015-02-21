/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback;

/**
 * Interface Injection: any bean implementing this interface is indicating that it was a feedback provider injected into it.
 */
public interface FeedbackAware
{
    /**
     * The provider the instance should use.
     * @return the provider instance.
     */
    FeedbackProvider getFeedbackProvider();

    /**
     * Specifies the provider this instance should use.
     * @param aProvider the provider to use.
     */
    void setFeedbackProvider( FeedbackProvider aProvider );
}
