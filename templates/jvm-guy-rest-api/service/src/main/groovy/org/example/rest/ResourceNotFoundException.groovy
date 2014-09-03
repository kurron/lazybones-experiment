package org.example.rest

import org.example.shared.feedback.FeedbackContext
import org.example.shared.feedback.UnrecoverableError

/**
 * Signals that the specified resource was not located.
 */
class ResourceNotFoundException extends UnrecoverableError {

    ResourceNotFoundException(FeedbackContext context, Object[] arguments) {
        super(context, arguments)
    }
}
