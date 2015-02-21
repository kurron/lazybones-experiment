/*
 * Copyright (c) 2015. Ronald D. Kurr kurr@jvmguy.com
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
package org.kurron.example.rest.inbound

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
