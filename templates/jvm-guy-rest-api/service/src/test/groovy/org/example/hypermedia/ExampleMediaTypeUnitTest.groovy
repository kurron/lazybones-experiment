package org.example.hypermedia

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.example.shared.BaseUnitTest
import spock.lang.Unroll

/**
 * Learning test to see how Jackson can be configured to deal with required and optional properties.
 */
class ExampleMediaTypeUnitTest extends BaseUnitTest {

    /**
     * Subject under test.
     */
    static def sut = new ObjectMapper()

    def setupSpec() {
        sut.enable( SerializationFeature.INDENT_OUTPUT )
        sut.disable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS )
        sut.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false )
        sut.setVisibility( PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE )
    }

    @Unroll( 'verify JSON serialization where #description' )
    def 'exercise serializing various degrees of completeness'() {

        when: 'media type is serialized into JSON'
        String json = sut.writeValueAsString( mediaType )

        then: 'JSON contains the expected properties'
        println json
        contains.every {
            json.contains( it )
        }
        skipped.every {
            !json.contains( it )
        }

        where:
        mediaType         | status            || contains                                                                     || skipped
        fullyPopulated()  | 'fully populated' || ['string-type', 'integer-type', 'boolean-type', 'array-type', 'object-type'] || []
        stringOnly()      | 'string only'     || ['string-type']                                                              || ['integer-type', 'boolean-type', 'array-type', 'object-type']
        integerOnly()     | 'integer only'    || ['integer-type']                                                             || ['string-type', 'boolean-type', 'array-type', 'object-type']
        booleanOnly()     | 'boolean only'    || ['boolean-type']                                                             || ['string-type', 'integer-type', 'array-type', 'object-type']
        arrayOnly()       | 'array only'      || ['array-type']                                                               || ['string-type', 'integer-type', 'boolean-type', 'object-type']
        objectOnly()      | 'object only'     || ['object-type']                                                              || ['string-type', 'integer-type', 'boolean-type', 'array-type']

        description = "the media type has a construction status of ${status}"
    }

    ExampleMediaType fullyPopulated() {
        def instance = new ExampleMediaType()
        instance.arrayType = ['foo']
        instance.booleanType = true
        instance.integerType = 21
        instance.objectType = ['year': 1964]
        instance.stringType = 'bar'
        instance
    }

    ExampleMediaType stringOnly() {
        def instance = new ExampleMediaType()
        instance.stringType = 'bar'
        instance
    }

    ExampleMediaType integerOnly() {
        def instance = new ExampleMediaType()
        instance.integerType = 21
        instance
    }

    ExampleMediaType booleanOnly() {
        def instance = new ExampleMediaType()
        instance.booleanType = true
        instance
    }

    ExampleMediaType arrayOnly() {
        def instance = new ExampleMediaType()
        instance.arrayType = ['foo']
        instance
    }

    ExampleMediaType objectOnly() {
        def instance = new ExampleMediaType()
        instance.objectType = ['year': 1964]
        instance
    }
}
