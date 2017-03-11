/*
 * Copyright (c) 2017. Ronald D. Kurr kurr@jvmguy.com
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
package org.kurron.example.cdc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

/**
 * An example from the documentation..
 **/
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@AutoConfigureWireMock( port = 1234 ) // <=== must match what the service is using
class HttpWireMockTest extends Specification {

    @Autowired
    OutboundService sut

    void 'exercise application startup'() {

        expect: 'configuration to be loaded correctly'
        sut
        stubFor( get( urlEqualTo( '/resource' ) ).willReturn( aResponse().withBody( 'Hello World!' ) ) )
        sut.go() == 'Hello World!'
    }
}
