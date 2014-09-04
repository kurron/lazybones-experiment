package org.example.shared.rest

import org.example.shared.feedback.FeedbackContext
import org.example.shared.feedback.UnrecoverableError

/**
 * A convenience class for all resource exceptions.  It contains state about the failure
 * that can be used for translation into the hypermedia control.
 */
class BaseResourceError extends UnrecoverableError {

    /**
     * Constant that signals that the property has not been properly set.
     */
    public static final String UNSET = 'unset'

    /**
     * Short name of the error context.
     */
    String title = UNSET

    /**
     * A code that uniquely identifies the error context.  Useful in operations
     * manuals.
     */
    String code = UNSET

    /**
     * A detailed description of the failure and possible solutions.
     */
    String message = UNSET

    /**
     * The feedback information useful in logging.
     */
    FeedbackContext context

    /**
     * Data to be substituted into the log message.
     */
    List<Object> arguments

    BaseResourceError( FeedbackContext aContext,
                       List<Object> someArguments,
                       String aTitle,
                       String aMessage ) {
        super( aContext, someArguments )
        title = aTitle
        code = aContext.code.toString()
        message = aMessage
        context = aContext
        arguments = someArguments
    }

}
