package org.example.shared

import groovy.transform.Canonical
import org.example.shared.feedback.BaseFeedbackAware
import org.springframework.data.mongodb.core.MongoOperations

/**
 * Convenience base class that all custom repository extensions should extend.
 */
@Canonical
class BaseRepository extends BaseFeedbackAware {

    /**
     * Manages interactions with the MongoDB database.
     */
    protected final MongoOperations theMongoDbTemplate

    protected BaseRepository( MongoOperations aTemplate ) {
        theMongoDbTemplate = aTemplate
    }
}
