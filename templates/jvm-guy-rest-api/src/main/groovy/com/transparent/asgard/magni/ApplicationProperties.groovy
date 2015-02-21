/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Application specific properties. This can be injected into beans to share values.
 */
@ConfigurationProperties( value = 'magni', ignoreUnknownFields = false )
class ApplicationProperties {

    /**
     * The maximum payload size that the server will accept, in Megabytes.
     */
    long maxPayloadSize

    /**
     * Flag controlling whether or not the correlation id is required.
     */
    boolean requireCorrelationId
}
