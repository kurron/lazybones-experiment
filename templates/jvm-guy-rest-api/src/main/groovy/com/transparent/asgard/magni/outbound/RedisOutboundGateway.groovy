/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.outbound

import static com.transparent.asgard.magni.feedback.MagniFeedbackContext.REDIS_RESOURCE_NOT_FOUND
import static com.transparent.asgard.magni.feedback.MagniFeedbackContext.REDIS_RETRIEVE_INFO
import static com.transparent.asgard.magni.feedback.MagniFeedbackContext.REDIS_STORE_INFO
import com.transparent.asgard.feedback.AbstractFeedbackAware
import com.transparent.asgard.feedback.exceptions.NotFoundError
import com.transparent.asgard.stereotype.OutboundGateway
import java.util.concurrent.TimeUnit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisOperations

/**
 * Implementation of the outbound gateway that talks to Redis.
 */
@OutboundGateway
class RedisOutboundGateway extends AbstractFeedbackAware implements PersistenceOutboundGateway {

    /**
     * Handles Redis interactions.
     */
    private final RedisOperations<UUID, byte[]> redisOperations

    /**
     * The key to use when storing and retrieving the resource's content type.
     */
    private static final String CONTENT_TYPE_KEY = 'content-type'

    /**
     * The key to use when storing and retrieving the resource's payload.
     */
    private static final String PAYLOAD_KEY = 'payload'

    @Autowired
    RedisOutboundGateway( final RedisOperations redisTemplate ) {
        redisOperations = redisTemplate // WARNING: bean name must be 'redisTemplate' or injection fails
    }

    @Override
    UUID store( final RedisResource resource, final long expirationSeconds ) {
        def generatedId = UUID.randomUUID()
        feedbackProvider.sendFeedback( REDIS_STORE_INFO, resource.payload.length, resource.contentType, expirationSeconds, generatedId )
        redisOperations.opsForHash().putAll( generatedId, [(CONTENT_TYPE_KEY): resource.contentType, (PAYLOAD_KEY): resource.payload] )
        redisOperations.expire( generatedId, expirationSeconds, TimeUnit.SECONDS )
        generatedId
    }

    @Override
    RedisResource retrieve( final UUID id ) {
        feedbackProvider.sendFeedback( REDIS_RETRIEVE_INFO, id )
        def entries = redisOperations.opsForHash().entries( id )
        if ( !entries ) {
            feedbackProvider.sendFeedback( REDIS_RESOURCE_NOT_FOUND, id )
            throw new NotFoundError( REDIS_RESOURCE_NOT_FOUND, id )
        }
        new RedisResource( payload: entries[PAYLOAD_KEY] as byte[], contentType: entries[CONTENT_TYPE_KEY] as String )
    }
}