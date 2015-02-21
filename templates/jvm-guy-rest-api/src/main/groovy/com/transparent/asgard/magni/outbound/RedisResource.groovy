/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.outbound

import groovy.transform.Canonical

/**
 * Represents a resource that is saved to Redis as a hash datatype.
 */
@Canonical
class RedisResource {

    /**
     * The content type associated with the resource.
     */
    String contentType

    /**
     * The resource bytes.
     */
    byte[] payload
}
