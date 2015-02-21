/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.outbound

import com.transparent.asgard.magni.Builder
import org.springframework.http.MediaType

/**
 * Constructs an RedisResource object with reasonable defaults.
 */
class RedisResourceBuilder extends Builder<RedisResource> {

    @Override
    RedisResource build() {
        new RedisResource( contentType: MediaType.APPLICATION_JSON_VALUE, payload: randomByteArray( 128 ) )
    }
}
