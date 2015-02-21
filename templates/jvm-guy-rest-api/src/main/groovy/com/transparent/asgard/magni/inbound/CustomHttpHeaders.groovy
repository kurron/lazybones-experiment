/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

/**
 * Contains custom HTTP header names.
 */
class CustomHttpHeaders {

    /**
     * Private constructor, to prevent instantiation.
     */
    private CustomHttpHeaders() { }

    /**
     * The correlation id (a.k.a. work-unit) header, useful in stitching together work being done by the server.
     */
    static final String X_CORRELATION_ID = 'X-Correlation-Id'

    /**
     * The number of minutes to wait before expiring a given resource.
     */
    static final String X_EXPIRATION_MINUTES = 'X-Expiration-Minutes'
}
