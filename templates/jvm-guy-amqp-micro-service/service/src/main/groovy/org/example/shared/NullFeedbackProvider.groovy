package org.example.shared

/**
 * Null Object Pattern: a no-op implementation of the feedback provider interface. Typically used only in testing
 * environments where real providers are not desired or a fallback is needed.
 */
class NullFeedbackProvider implements FeedbackProvider {
    @Override
    void sendFeedback( final FeedbackContext context, final Object[] arguments ) {
        // do nothing
    }

    @Override
    void sendFeedback( final FeedbackContext context, final Throwable error ) {
        // do nothing
    }
}
