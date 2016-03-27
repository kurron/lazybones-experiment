package org.kurron.example.outbound

import feign.Response
import feign.codec.ErrorDecoder

/**
 * Custom error handler that can transform a general exception into an application exception.
 */
class CustomErrorDecoder implements ErrorDecoder {
    @Override
    Exception decode( String methodKey, Response response ) {
        println "Error dected in ${methodKey}. Transforming ${response.status()} ${response.reason()}"
        new UnsupportedOperationException( methodKey )
    }
}
