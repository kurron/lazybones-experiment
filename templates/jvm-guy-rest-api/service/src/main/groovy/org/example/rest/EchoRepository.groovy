package org.example.rest

import org.example.rest.model.Item

/**
 * Knows how to locate echo resources.
 */
interface EchoRepository {

    /**
     * Loads the specified item from durable storage.
     * @param id which item to load.
     * @return loaded item.
     */
    Item findOne( String id )
}
