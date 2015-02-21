/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni

import com.fasterxml.jackson.databind.ObjectMapper
import com.transparent.asgard.magni.categories.IntegrationTests
import org.junit.experimental.categories.Category
import org.springframework.boot.test.TestRestTemplate
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.client.RestOperations
import org.springframework.web.util.UriComponentsBuilder

/**
 * Base class for integration-level tests.
 */
@SuppressWarnings( ['GStringExpressionWithinString'] )
@Category( IntegrationTests )
abstract class BaseIntegrationTest extends BaseTest {

    protected int getPort() {
        System.properties['integration.test.port'] as int
    }

    protected RestOperations getRestOperations() {
        new TestRestTemplate()
    }

    protected ObjectMapper getMapper() {
        new Jackson2ObjectMapperBuilder().build()
    }

    protected URI getServerUri() {
        UriComponentsBuilder.newInstance()
                .scheme( 'http' )
                .host( 'localhost' )
                .port( port )
                .path( '/' )
                .build()
                .toUri()
    }
}
