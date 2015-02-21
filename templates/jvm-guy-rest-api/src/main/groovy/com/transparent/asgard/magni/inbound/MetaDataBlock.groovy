/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * Optional meta-data section of the control.
 */
@Canonical
class MetaDataBlock {

    /**
     * RFC 2046 MIME type.
     **/
    @JsonProperty( 'mime-type' )
    String mimeType

    /**
     * The size, in bytes, of the stored asset.
     **/
    @JsonProperty( 'content-length' )
    int contentLength
}
