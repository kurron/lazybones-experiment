package org.example.rest

import org.example.rest.model.Item
import org.springframework.stereotype.Repository

/**
 * Implementation is strictly for illustrating testing scenarios.
 */
@Repository
class DummyEchoRepository implements EchoRepository {

    @Override
    Item findOne( String id ) {
        def item = new Item( instance: id, text: 'some random text' )
        id == 'invalid' ? null : item
    }
}
