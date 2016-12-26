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
package org.kurron.example.core

import java.time.Instant
import org.junit.experimental.categories.Category
import org.kurron.categories.UnitTest
import org.kurron.example.outbound.TimeService
import org.kurron.traits.GenerationAbility
import spock.lang.Specification

/**
 * A unit-level test of the DefaultComponent object.
 **/
@Category( UnitTest )
class DefaultTimeComponentUnitTest extends Specification implements GenerationAbility {

    def 'exercise currentTime happy path'() {
        given: 'a subject under test'
        def stub = Stub( TimeService )
        stub.checkTheTime() >> Instant.EPOCH

        def sut = new DefaultTimeComponent( stub )

        when: 'we call currentTime'
        def results = sut.currentTime()

        then: 'we get expected results'
        Instant.EPOCH.epochSecond == results.epochSecond
    }
}
