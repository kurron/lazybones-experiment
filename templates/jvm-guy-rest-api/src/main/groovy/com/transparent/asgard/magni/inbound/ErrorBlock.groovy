/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Optional error section of the control.
 */
@Canonical
class ErrorBlock {

    /**
     * Number uniquely describing the error conditions.
     **/
    @JsonProperty( 'code' )
    int code

    /**
     * Details the error condition.
     **/
    @JsonProperty( 'message' )
    String message

    /**
     * Details the error condition in language targeted towards the developer.
     **/
    @JsonProperty( 'developer-message' )
    String developerMessage
}
