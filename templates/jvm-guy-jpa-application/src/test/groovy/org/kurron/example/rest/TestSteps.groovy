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
package org.kurron.example.rest

import com.fasterxml.jackson.databind.ObjectMapper
import cucumber.api.java.After
import cucumber.api.java.Before
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestOperations

/**
 * Step definitions geared towards the application's acceptance test but remember, all steps are used
 * by Cucumber unless special care is taken. If you word your features in a consistent manner, then
 * steps will automatically get reused and you won't have to keep writing the same test code.
 **/
@ContextConfiguration( classes = [AcceptanceTestConfiguration], loader = SpringApplicationContextLoader )
@Slf4j
@SuppressWarnings( ['GrMethodMayBeStatic', 'GStringExpressionWithinString'] )
class TestSteps {

    /**
     * The application's configuration settings.
     **/
    @Autowired
    private ApplicationProperties configuration

    @Autowired
    private RestOperations restOperations

    @Autowired
    private ObjectMapper objectMapper

    /**
     * Knows how to determine the service that the application is listening on.
     **/
    @Autowired
    private HttpServiceResolver theServiceResolver

    /**
     * Constant for unknown media type.
     **/
    public static final String ALL = '*'

    /**
     * Default size of uploaded payload.
     **/
    public static final int BUFFER_SIZE = 256

    /**
     * Number of Bytes in a Megabyte.
     */
    @SuppressWarnings( 'DuplicateNumberLiteral' )
    public static final int BYTES_IN_A_MEGABYTE = 1024 * 1024

    /**
     * Generates random data.
     **/
    @SuppressWarnings( 'UnusedPrivateField' )
    @Delegate
    private final Randomizer randomizer = new Randomizer()

    /**
     * This is state shared between steps and can be setup and torn down by the hooks.
     **/
    class MyWorld {
        def internet = restOperations
        def transformer = objectMapper
    }

    /**
     * Shared between hooks and steps.
     **/
    MyWorld sharedState

    @Before
    void assembleSharedState() {
        log.info( 'Creating shared state' )
        sharedState = new MyWorld()
        log.error( 'theServiceResolver is {}', theServiceResolver )
        log.error( 'sharedState.uri is {}', sharedState.uri )
    }

    @After
    void destroySharedState() {
        log.info( 'Destroying shared state' )
        sharedState = null
    }
}
