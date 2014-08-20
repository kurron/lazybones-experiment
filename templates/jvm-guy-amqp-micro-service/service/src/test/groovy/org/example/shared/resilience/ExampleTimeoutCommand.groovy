package org.example.shared.resilience
/**
 * Created by vagrant on 8/20/14.
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
