/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.feedback

import static com.transparent.asgard.feedback.Audience.DEVELOPMENT
import static com.transparent.asgard.feedback.Audience.QA
import static com.transparent.asgard.feedback.FeedbackLevel.ERROR
import static com.transparent.asgard.feedback.FeedbackLevel.INFO
import com.transparent.asgard.feedback.Audience
import com.transparent.asgard.feedback.FeedbackContext
import com.transparent.asgard.feedback.FeedbackLevel

/**
 * Message codes specific to Magni.
 */
@SuppressWarnings( ['LineLength'] )
enum MagniFeedbackContext implements FeedbackContext {

    GENERIC_ERROR( 2000, 'The following error has occurred and was caught by the global error handler: {}', ERROR, QA ),
    REDIS_STORE_INFO( 2001, 'Storing a {} byte payload with a content type of {} in Redis for {} seconds with a key of {}', INFO, DEVELOPMENT ),
    REDIS_RETRIEVE_INFO( 2002, 'Retrieving payload from Redis with a key of {}', INFO, DEVELOPMENT ),
    REDIS_RESOURCE_NOT_FOUND( 2003, 'The resource with an id of {} was not found in the system', ERROR, QA ),
    PRECONDITION_FAILED( 2004, 'The required {} header was not found on an inbound REST request', ERROR, QA ),
    CONTENT_LENGTH_REQUIRED( 2005, 'The Content-Length was not set and is required', ERROR, QA ),
    PAYLOAD_TOO_LARGE( 2006, 'The payload size of {} Bytes exceeds the maximum permitted size of {} Megabytes', ERROR, QA ),
    MISSING_CORRELATION_ID( 2007, 'A correlation id was missing from a request, and an auto-generated id of {} will be used instead', FeedbackLevel.WARN, QA )

    /**
     * Unique context code for this instance.
     */
    private final int theCode

    /**
     * Message format string for this instance.
     */
    private final String theFormatString

    /**
     * Feedback level for this instance.
     */
    private final FeedbackLevel theFeedbackLevel

    /**
     * The audience for this instance.
     */
    private final Audience theAudience

    MagniFeedbackContext( int aCode, String aFormatString, FeedbackLevel aFeedbackLevel, Audience anAudience ) {
        theCode = aCode
        theFormatString = aFormatString
        theFeedbackLevel = aFeedbackLevel
        theAudience = anAudience
    }

    @Override
    int getCode() {
        theCode
    }

    @Override
    String getFormatString() {
        theFormatString
    }

    @Override
    FeedbackLevel getFeedbackLevel() {
        theFeedbackLevel
    }

    @Override
    Audience getAudience() {
        theAudience
    }
}
