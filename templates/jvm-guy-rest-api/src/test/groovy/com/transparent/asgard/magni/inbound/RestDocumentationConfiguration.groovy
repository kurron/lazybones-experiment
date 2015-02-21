/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.web.servlet.request.RequestPostProcessor
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcConfigurerAdapter
import org.springframework.web.context.WebApplicationContext

/**
 * Simple configuration used by the REST documentation generator.
 **/
class RestDocumentationConfiguration extends MockMvcConfigurerAdapter {

    private String scheme = 'http'

    private String host = 'api.example.com'

    private int port = 8080

    RestDocumentationConfiguration withScheme( String aScheme ) {
        scheme = aScheme
        this
    }

    RestDocumentationConfiguration withHost( String aHost ) {
        host = aHost
        this
    }

    RestDocumentationConfiguration withPort( int aPort ) {
        port = aPort
        this
    }

    @Override
    RequestPostProcessor beforeMockMvcCreated( ConfigurableMockMvcBuilder<?> builder, WebApplicationContext context) {
        new RequestPostProcessor() {

            @Override
            MockHttpServletRequest postProcessRequest( MockHttpServletRequest request) {
                request.setScheme( RestDocumentationConfiguration.this.scheme )
                request.setRemotePort( RestDocumentationConfiguration.this.port )
                request.setServerPort( RestDocumentationConfiguration.this.port )
                request.setRemoteHost( RestDocumentationConfiguration.this.host )
                request
            }
        }
    }

}
