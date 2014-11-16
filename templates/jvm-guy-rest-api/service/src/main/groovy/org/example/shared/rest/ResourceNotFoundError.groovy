package org.example.shared.rest

import static org.example.shared.feedback.CustomFeedbackContext.RESOURCE_NOT_FOUND
import org.example.shared.feedback.FeedbackContext
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Signals that the specified resource was not located.
 */
@SuppressWarnings( 'UnnecessaryCast' )
@ResponseStatus( value = HttpStatus.NOT_FOUND, reason = 'unknown' ) // mostly for insurance
class ResourceNotFoundError extends BaseResourceError {

    /**
     * Indicates a failure to locate a specific resource in the system.
     * @param identity the identifier of the resource.
     * @param aTitle short description of the issue.
     * @param aMessage detailed explanation of the failure.
     */
    ResourceNotFoundError( String identity, String aTitle, String aMessage ) {
        super( RESOURCE_NOT_FOUND as FeedbackContext, [identity] as List<Object>, aTitle, aMessage )
    }
}
