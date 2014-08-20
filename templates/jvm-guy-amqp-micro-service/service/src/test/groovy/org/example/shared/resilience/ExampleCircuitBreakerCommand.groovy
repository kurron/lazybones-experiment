package org.example.shared.resilience

/**
 * A command the showcases how to protect against flickering integration points.
 */
class ExampleCircuitBreakerCommand extends CircuitBreakerCommand<String> {

    private final FlickeringResource theResource

    ExampleCircuitBreakerCommand( String commandKey,
                                  FlickeringResource aResource,
                                  boolean forceOpen = false ) {
        super( 'group', commandKey, forceOpen )
        theResource = aResource
    }

    @Override
    protected String run() throws Exception {
        theResource.fetchResource()
    }

    @Override
    protected String getFallback() {
        'Resource unavailable!'
    }
}
