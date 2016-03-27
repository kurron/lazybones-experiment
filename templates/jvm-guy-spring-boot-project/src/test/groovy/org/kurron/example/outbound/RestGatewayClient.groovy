package org.kurron.example.outbound

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

/**
 * Dedicated HTTP client that knows only how to talk to the example service.
 */
@FeignClient( name = 'example',
              configuration = RestGatewayClientConfiguration,
              fallback = RestGatewayClientFallback )
interface RestGatewayClient {

    @RequestMapping( method = RequestMethod.GET,
                     path = '/descriptor/application',
                     consumes = 'application/json;type=FIXME;version=1.0.0' )
    String happyPath()

    @RequestMapping( method = RequestMethod.GET,
                     path = '/descriptor/fail',
                     consumes = 'application/json;type=FIXME;version=1.0.0' )
    String systemFailure()

    @RequestMapping( method = RequestMethod.GET,
                     path = '/descriptor/fail/application',
                     consumes = 'application/json;type=FIXME;version=1.0.0' )
    String applicationFailure()
}