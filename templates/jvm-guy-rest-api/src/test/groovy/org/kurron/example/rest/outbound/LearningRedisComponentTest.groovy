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
package org.kurron.example.rest.outbound

import java.util.concurrent.TimeUnit
import org.kurron.example.rest.BaseOutboundIntegrationTest

/**
 * A simple learning test to exercise Spring Data Redis.
 */
class LearningRedisComponentTest extends BaseOutboundIntegrationTest {

    def 'work with redis'() {

        given: 'valid redis operations'
        assert redisTemplate

        and: 'an id and binary asset to save'
        def id = randomUUID()
        def asset = randomByteArray( 128 )

        when: 'the asset is saved to redis'
        redisTemplate.opsForValue().set( id, asset )

        then: 'the asset can be retrieved'
        def result = redisTemplate.opsForValue().get( id )

        and: 'the expected asset is returned'
        result
        result == asset

        when: 'the asset is deleted'
        redisTemplate.delete( id )

        then: 'the asset is no longer returned'
        redisTemplate.opsForValue().get( id ) == null  // don't rely on groovy truth, make sure the result is null
    }

    def 'work with redis hashes'() {

        given: 'valid redis operations'
        assert redisTemplate

        and: 'an id and binary asset to save'
        def id = randomUUID()
        def asset = randomByteArray( 128 )

        when: 'the asset is saved to redis'
        redisTemplate.opsForHash().put( id, 'content-type', 'application/json' )
        redisTemplate.opsForHash().put( id, 'payload', asset )

        then: 'the asset can be retrieved'
        redisTemplate.opsForHash().get( id, 'content-type' ) == 'application/json'
        redisTemplate.opsForHash().get( id, 'payload' ) == asset

        and: 'use bound hash operations instead'
        def boundHashOps = redisTemplate.boundHashOps( id )
        boundHashOps.get( 'content-type' ) == 'application/json'
        boundHashOps.get( 'payload' ) == asset

        and: 'get values in single operation'
        def multiGetResults = boundHashOps.multiGet( ['content-type', 'payload'] )
        multiGetResults[0] == 'application/json'
        multiGetResults[1] == asset

        and: 'ensure that order of keys matches order of results'
        def moreResults = boundHashOps.multiGet( ['payload', 'content-type'] )
        moreResults[0] == asset
        moreResults[1] == 'application/json'

        and: 'write multiple hash values at once'
        boundHashOps.putAll( ['3rd': 'third', '4th': 'fourth'] )

        and: 'read them back out as a map'
        def mappedResults = boundHashOps.entries()
        mappedResults['3rd'] == 'third'
        mappedResults['4th'] == 'fourth'
        mappedResults['payload'] == asset
        mappedResults['content-type'] == 'application/json'

        and: 'expire the entire hash and ensure resource is deleted'
        boundHashOps.expire( 1, TimeUnit.NANOSECONDS )
        sleep 1
        !redisTemplate.hasKey( id )
    }
}
