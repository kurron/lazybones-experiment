package org.example.shared.resilience

/**
 * A command the showcases how to timeout potentially latent integration points.
 */
class ExampleTimeoutCommand extends TimeoutCommand<String> {

    private final LatentResource theResource

    ExampleTimeoutCommand( LatentResource aResource) {
        super( 'timeout example', 100 )
        theResource = aResource
    }

    @Override
    protected String run() throws Exception {
        theResource.fetchResource()
    }

    @Override
    protected String getFallback() {
        'Resource timed out!'
    }
}
