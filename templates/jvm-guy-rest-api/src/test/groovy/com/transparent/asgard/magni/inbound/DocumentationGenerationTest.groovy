/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

import static org.springframework.restdocs.core.RestDocumentation.document
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.transparent.asgard.magni.BaseComponentTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * This 'test' generates code snippets used in the REST documentation.  We need to leverage the
 * integration testing facilities so we can execute calls against a running server and capture
 * actual traffic.
 */
@SuppressWarnings( 'UnnecessaryGetter' )
class DocumentationGenerationTest extends BaseComponentTest {

    @Autowired
    private WebApplicationContext context

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup( this.context ).apply( new RestDocumentationConfiguration() ).build()
    }

    def 'demonstrate api discovery'() {

        given: 'a valid request'
        def requestBuilder = get( '/' ).accept( MagniControl.MIME_TYPE )
                                       .header( CustomHttpHeaders.X_CORRELATION_ID, '155887a0-8959-4031-a30a-a8e52bc6b7d8' )

        when: 'the GET request is made'
        mockMvc.perform( requestBuilder ).andExpect( status().isOk() ).andDo( document( 'api-discovery' ) )

        then: 'examples are generated'
    }

    def 'demonstrate failure scenario'() {

        given: 'a valid request'
        def requestBuilder = get( '/{id}', randomUUID() ).accept( 'image/png;width=1024;height=768', MagniControl.MIME_TYPE )
                                                         .header( CustomHttpHeaders.X_CORRELATION_ID, '155887a0-8959-4031-a30a-a8e52bc6b7d8' )

        when: 'the GET request is made'
        mockMvc.perform( requestBuilder ).andExpect( status().isNotFound() ).andDo( document( 'failure-scenario' ) )

        then: 'examples are generated'
    }

    def 'demonstrate asset storage'() {

        given: 'a valid request'
        def buffer = 'some image bytes'.bytes
        def requestBuilder = post( '/' ).content( buffer )
                .contentType( 'image/png;width=1024;height=768' )
                .accept( MagniControl.MIME_TYPE )
                .header( 'Content-Length', buffer.size() )
                .header( CustomHttpHeaders.X_EXPIRATION_MINUTES, 10 )
                .header( CustomHttpHeaders.X_CORRELATION_ID, '155887a0-8959-4031-a30a-a8e52bc6b7d8' )

        when: 'the POST request is made'
        mockMvc.perform( requestBuilder ).andExpect( status().isCreated() ).andDo( document( 'asset-storage' ) )

        then: 'examples are generated'
    }

    def 'demonstrate asset retrieval'() {

        given: 'a previously uploaded asset'
        def buffer = 'some image bytes'.bytes
        def uploadBuilder = post( '/' ).content( buffer )
                .contentType( 'image/png;width=1024;height=768' )
                .accept( MagniControl.MIME_TYPE )
                .header( 'Content-Length', buffer.size() )
                .header( CustomHttpHeaders.X_EXPIRATION_MINUTES, 10 )
                .header( CustomHttpHeaders.X_CORRELATION_ID, '155887a0-8959-4031-a30a-a8e52bc6b7d8' )
        def upload = mockMvc.perform( uploadBuilder ).andExpect( status().isCreated() ).andReturn()

        when: 'the GET request is made'
        def downloadBuilder = get( upload.response.getHeaderValue( 'Location' ) as String )
                .accept( 'image/png', 'application/json' )
                .header( CustomHttpHeaders.X_CORRELATION_ID, '00588700-8959-4031-a30a-a8e52bc6b7d8' )
        mockMvc.perform( downloadBuilder ).andExpect( status().isOk(  ) ).andDo( document( 'asset-download' ) )

        then: 'examples are generated'
    }
}
