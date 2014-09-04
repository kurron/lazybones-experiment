package org.example.hypermedia

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * This is an example of a hypermedia type.  This single resource will be used to support all aspects of
 * the API and, as such, must be serialized in such a fashion that parts that do not make sense in the
 * current context do not get transferred to the client.
 */
@JsonInclude( JsonInclude.Include.NON_NULL )
class ExampleMediaType {

    @JsonProperty( 'string-type' )
    String stringType

    @JsonProperty( 'integer-type' )
    String integerType

    @JsonProperty( 'boolean-type' )
    String booleanType

    @JsonProperty( 'array-type' )
    List<String> arrayType

    @JsonProperty( 'object-type' )
    Map<String,String> objectType
}
