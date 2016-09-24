/*
 * Copyright (c) 2016. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kurron.example.inbound.rest

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.junit.Rule
import org.junit.experimental.categories.Category
import org.kurron.categories.DocumentationTest
import org.kurron.example.Application
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.hypermedia.HypermediaDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

/**
 * This 'test' generates code snippets used in the REST documentation.
 *
 * See http://docs.spring.io/spring-restdocs/docs/1.0.1.RELEASE/reference/html5/ for details on how to use the documentation tool.
 **/
@Category( DocumentationTest )
@WebIntegrationTest( randomPort = true )
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class DocumentationGenerationTest extends Specification implements GenerationAbility {

    @Autowired
    private WebApplicationContext context

    @Rule
    JUnitRestDocumentation documentation = new JUnitRestDocumentation( 'build/generated-snippets' )

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup( context ).apply( documentationConfiguration( documentation ) ).build()
    }

    // showcases common links that appear in most responses and is a way to reuse snippets
    def pagingLinks = HypermediaDocumentation.links(
            HypermediaDocumentation.linkWithRel( 'first' ).optional().description( 'The first page of results' ),
            HypermediaDocumentation.linkWithRel( 'last' ).optional().description( 'The last page of results' ),
            HypermediaDocumentation.linkWithRel( 'next' ).optional().description( 'The next page of results' ),
            HypermediaDocumentation.linkWithRel( 'prev' ).optional().description( 'The previous page of results' )
    )

    def 'demonstrate failure scenario'() {

        given: 'a valid request'
        def requestBuilder = get( '/descriptor/fail/application' ).accept( HypermediaControl.MEDIA_TYPE ).header( 'X-Correlation-Id', randomUUID() )

        when: 'the GET request is made'
        def responseFields = PayloadDocumentation.responseFields(
                PayloadDocumentation.fieldWithPath( 'status' ).description( 'HTTP status code' ),
                PayloadDocumentation.fieldWithPath( 'error.code' ).description( 'application specific error code' ),
                PayloadDocumentation.fieldWithPath( 'error.message' ).description( 'description of the failure suitable for use by the UI' ),
                PayloadDocumentation.fieldWithPath( 'error.developer-message' ).description( 'description of the failure intended for support and development' )
        )
        mockMvc.perform( requestBuilder )
               .andExpect( status().isIAmATeapot() )
               .andDo( document( 'failure-scenario', pagingLinks, responseFields ) )

        then: 'examples are generated'
    }

    def 'demonstrate GET my resource'() {

        given: 'a valid request'
        def requestBuilder = get( '/descriptor/application' ).accept( HypermediaControl.MEDIA_TYPE ).header( 'X-Correlation-Id', randomUUID() )

        when: 'the GET request is made'
        mockMvc.perform( requestBuilder ).andExpect( status().isOk() ).andDo( document( 'get-my-resource' ) )

        then: 'examples are generated'
    }
}
