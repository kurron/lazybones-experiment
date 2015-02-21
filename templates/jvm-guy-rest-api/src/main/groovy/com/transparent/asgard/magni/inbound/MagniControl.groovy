/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import org.springframework.hateoas.ResourceSupport
import org.springframework.http.MediaType

/**
 * The hypermedia REST control for the Magni resource.  Can be serialized into
 * both JSON and, potentially, XML.
 */
@Canonical
@JsonInclude( JsonInclude.Include.NON_NULL )
class MagniControl extends ResourceSupport {

    /**
     * The expected MIME type for the control.
     */
    public static final String MIME_TYPE = 'application/json;type=magni;version=1.0.0'

    /**
     * Convenience form of the MIME-TYPE for Spring MVC APIs.
     **/
    public static final MediaType MEDIA_TYPE = MediaType.parseMediaType( MIME_TYPE )

    /**
     * The HTTP status code. We put it here in case the client isn't allowed access to the headers.
     */
    @JsonProperty( 'http-code' )
    int httpCode = Integer.MIN_VALUE

    @JsonProperty( 'meta-data' )
    MetaDataBlock metaDataBlock

    @JsonProperty( 'error' )
    ErrorBlock errorBlock
}
