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

import spock.lang.Requires

/**
 * Examples of some of the newer Spock features.
 */
class SpockExamplesUnitTest extends BaseUnitTest {

    def configuration = new ApplicationPropertiesBuilder().build()
    def sut = new CorrelationIdHandlerInterceptor( configuration )

    @Requires( { env.containsKey( 'RUN_SPECIAL_TEST' ) } )
    def 'run only if an environmental variable is set'() {

        expect: 'should never be run'
        false
    }

    @Requires( { System.getProperty( 'os.arch' ) == 'amd64' } )
    def 'run only if a system property is set'() {

        expect: 'only run on 64-bit systems'
        println 'Running on a 64-bit system'
    }

}
