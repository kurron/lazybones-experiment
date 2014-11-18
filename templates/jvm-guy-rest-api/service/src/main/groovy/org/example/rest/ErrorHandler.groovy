package org.example.rest

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.NOT_FOUND
import org.example.rest.model.ErrorContext
import org.example.rest.model.SimpleMediaType
import org.example.shared.rest.ResourceNotFoundError
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Global error handler.
 */
@ControllerAdvice
class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    ResponseEntity<SimpleMediaType> handleResourceNotFound( ResourceNotFoundError resourceError ) {
        def errorContext = new ErrorContext()
        errorContext.title = resourceError.title
        errorContext.code = resourceError.code
        errorContext.message = resourceError.message
        new ResponseEntity<SimpleMediaType>( new SimpleMediaType( error: errorContext ), NOT_FOUND )
    }

    @ExceptionHandler
    ResponseEntity<SimpleMediaType> handleUnexpectedFailure( Throwable exception ) {
        def errorContext = new ErrorContext()
        errorContext.title = 'Unexpected failure'
        errorContext.code = errorContext.getClass().name
        errorContext.message = exception.message
        new ResponseEntity<SimpleMediaType>( new SimpleMediaType( error: errorContext ), INTERNAL_SERVER_ERROR )
    }
}
