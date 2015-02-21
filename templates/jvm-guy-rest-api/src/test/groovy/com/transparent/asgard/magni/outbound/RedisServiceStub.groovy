/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.outbound

import com.transparent.asgard.feedback.AbstractFeedbackAware
import com.transparent.asgard.stereotype.ServiceStub

/**
 * A Service Stub of the PersistenceOutboundGateway useful in testing.
 */
@ServiceStub
class RedisServiceStub extends AbstractFeedbackAware implements PersistenceOutboundGateway {

    /**
     * A fake version of redis.
     **/
    private final Map<UUID,RedisResource> redis = [:]

    @Override
    UUID store( final RedisResource resource, final long expirationSeconds ) {
        def id = UUID.randomUUID()
        redis[id] = resource
        id
    }

    @Override
    RedisResource retrieve( final UUID id ) {
        redis[id]
    }
}
