package org.example.shared.rest

import org.example.shared.feedback.CustomFeedbackContext

/**
 * Signals that the specified resource was not located.
 */
class ResourceNotFoundError extends BaseResourceError {

    /**
     * Indicates a failure to locate a specific resource in the system.
     * @param identity the identifier of the resource.
     * @param aTitle short description of the issue.
     * @param aMessage detailed explanation of the failure.
     */
    ResourceNotFoundError( String identity, String aTitle, String aMessage ) {
        super( CustomFeedbackContext.RESOURCE_NOT_FOUND, [identity], aTitle, aMessage )
    }
}