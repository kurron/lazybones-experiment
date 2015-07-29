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
package org.kurron.example.lambda

import com.amazonaws.services.lambda.runtime.Context
import org.junit.experimental.categories.Category
import org.kurron.categories.UnitTest
import spock.lang.Specification

/**
 * Unit-level test of the StreamLambda object.
 **/
@Category( UnitTest )
class StreamLambdaUnitTest extends Specification {

    def allLowerCase = 'all lower case'
    def context = Stub( Context )
    def 'test happy path'() {

        given:
        def sut = new StreamLambda()
        def input = new ByteArrayInputStream( allLowerCase.bytes )
        def output = new ByteArrayOutputStream( 1024 )

        when:
        sut.handleRequest( input, output, context )


        then:
        def bytes = output.toByteArray()
        def response = new String( bytes )
        response == allLowerCase.toUpperCase()
    }
}
