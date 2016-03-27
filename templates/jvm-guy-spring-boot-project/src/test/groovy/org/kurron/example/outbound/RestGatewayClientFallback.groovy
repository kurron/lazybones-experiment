package org.kurron.example.outbound

/**
 * What value to return if the downstream service has issues.
 *
 * A warning from the Spring Cloud documentation:
 *
 * There is a limitation with the implementation of fallbacks in Feign and how Hystrix fallbacks work.
 * Fallbacks are currently not supported for methods that return com.netflix.hystrix.HystrixCommand
 * and rx.Observable.
 */
class RestGatewayClientFallback implements RestGatewayClient {

    @Override
    String happyPath() {
        'happyPath fallback returned'
    }

    @Override
    String systemFailure() {
        'systemFailure fallback returned'
    }

    @Override
    String applicationFailure() {
        'applicationFailure fallback returned'
    }
}
