/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.outbound

/**
 * Interacts with the outbound persistence layer.
 */
interface PersistenceOutboundGateway {

    /**
     * Store the provided resource.
     * @param resource the resource to store, which includes the content type and bytes.
     * @param expirationSeconds the number of seconds to wait before expiring the resource.
     * @return the assigned id of the stored resource.
     */
    UUID store( final RedisResource resource, final long expirationSeconds )

    /**
     * Retrieves the resource associated with the provided id.
     * @param id the id of the resource to retrieve.
     * @return the resource.
     */
    RedisResource retrieve( final UUID id )
}
