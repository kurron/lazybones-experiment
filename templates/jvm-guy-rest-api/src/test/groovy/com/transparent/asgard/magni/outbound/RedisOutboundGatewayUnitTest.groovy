/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.outbound

import static com.transparent.asgard.magni.feedback.MagniFeedbackContext.REDIS_RESOURCE_NOT_FOUND
import static com.transparent.asgard.magni.outbound.RedisOutboundGateway.CONTENT_TYPE_KEY
import static com.transparent.asgard.magni.outbound.RedisOutboundGateway.PAYLOAD_KEY
import com.transparent.asgard.feedback.exceptions.NotFoundError
import com.transparent.asgard.magni.BaseUnitTest
import java.util.concurrent.TimeUnit
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisOperations

/**
 * Unit-level testing of the RedisOutboundGateway object.
 */
class RedisOutboundGatewayUnitTest extends BaseUnitTest {

    def redisOperations = Mock( RedisOperations )
    def hashOperations = Mock( HashOperations )
    def sut = new RedisOutboundGateway( redisOperations )
    def redisResource = new RedisResourceBuilder().build()
    def expectedRedisEntries = [(CONTENT_TYPE_KEY): redisResource.contentType, (PAYLOAD_KEY): redisResource.payload]

    def 'exercise resource storage'() {

        given: 'seconds to wait until the resource is expired'
        def expirationSeconds = randomNumberExclusive( 10 )

        when: 'the resource is stored'
        def result = sut.store( redisResource, expirationSeconds )

        then: 'the resource is stored in redis as expected'
        1 * redisOperations.opsForHash() >> hashOperations
        1 * hashOperations.putAll( !null as UUID, expectedRedisEntries )

        and: 'the expected expiration duration is set'
        1 * redisOperations.expire( !null as UUID, expirationSeconds, TimeUnit.SECONDS )

        and: 'a valid id is returned'
        result
        result == UUID.fromString( result.toString() )
    }

    def 'exercise resource retrieval'() {

        given: 'a resource id'
        def id = randomUUID()

        when: 'the resource is retrieved by id'
        def result = sut.retrieve( id )

        then: 'redis is called as expected'
        1 * redisOperations.opsForHash() >> hashOperations
        1 * hashOperations.entries( id ) >> expectedRedisEntries

        and: 'the expected resource is returned'
        result == redisResource
    }

    def 'exercise resource not found error handling'() {

        given: 'a resource id'
        def id = randomUUID()

        when: 'the resource is retrieved by id'
        sut.retrieve( id )

        then: 'no data is found in redis'
        1 * redisOperations.opsForHash() >> hashOperations
        1 * hashOperations.entries( id ) >> [:]

        and: 'the expected error is thrown'
        def error = thrown( NotFoundError )
        error.code == REDIS_RESOURCE_NOT_FOUND.code
    }
}
