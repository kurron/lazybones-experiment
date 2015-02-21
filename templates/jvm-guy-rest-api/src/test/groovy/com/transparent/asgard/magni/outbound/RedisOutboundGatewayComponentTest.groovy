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
package com.transparent.asgard.magni.outbound

import org.kurron.feedback.exceptions.NotFoundError
import com.transparent.asgard.magni.BaseComponentTest
import com.transparent.asgard.magni.feedback.MagniFeedbackContext
import org.springframework.beans.factory.annotation.Autowired

/**
 * Integration-level testing of the RedisOutboundGateway object.
 */
class RedisOutboundGatewayComponentTest extends BaseComponentTest {

    @Autowired
    PersistenceOutboundGateway sut

    def 'exercise storage and retrieval'() {

        given: 'a valid outbound gateway'
        assert sut

        and: 'a resource to save'
        def resource = new RedisResourceBuilder().build()

        and: 'seconds to wait until expiring the resource'
        def expirationSeconds = 5

        when: 'the resource is saved to redis'
        def id = sut.store( resource, expirationSeconds )

        then: 'the resource can be retrieved immediately'
        def result = sut.retrieve( id )

        and: 'the expected bytes are returned'
        result
        result == resource

        when: 'the resource is retrieved again after the expiration duration'
        sleep expirationSeconds * 1000
        sut.retrieve( id )

        then: 'the resource is no longer available'
        def error = thrown( NotFoundError )
        error.code == MagniFeedbackContext.REDIS_RESOURCE_NOT_FOUND.code
    }
}
