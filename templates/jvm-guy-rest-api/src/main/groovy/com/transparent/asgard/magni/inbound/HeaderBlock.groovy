/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Describes an HTTP header required for a request.
 */
@Canonical
class HeaderBlock {

    /**
     * Name of the required HTTP header.
     **/
    @JsonProperty( 'header' )
    String header

    /**
     * An example of a valid header value.
     **/
    @JsonProperty( 'example' )
    String example
}
