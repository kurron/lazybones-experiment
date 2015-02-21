/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.outbound

import com.transparent.asgard.feedback.exceptions.NotFoundError
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
