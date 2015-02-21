/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni.inbound

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.transparent.asgard.magni.BaseUnitTest
import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

/**
 * Unit-level tests validating the various conversions of the MagniControl.
 */
@SuppressWarnings( 'UnnecessaryGetter' )
class MagniControlConversionUnitTest extends BaseUnitTest {

    ObjectMapper sut = new Jackson2ObjectMapperBuilder().featuresToEnable( SerializationFeature.INDENT_OUTPUT ).build()

    def 'exercise empty control'() {

        given: 'an empty control'
        def control = new MagniControl()

        when: 'the object is transformed into JSON'
        def json = sut.writeValueAsString( control )

        then: 'JSON is mostly empty'
        json.contains( 'links' ) // links are always added because of ResourceSupport
    }

    def 'exercise error block'() {

        given: 'a control with only error section in it'
        def control = new MagniControl().with {
            httpCode = HttpStatus.FORBIDDEN.value()
            errorBlock = new ErrorBlock( code: randomInteger(),
                                         message: randomHexString(),
                                         developerMessage: randomHexString() )
            it
        }

        when: 'the object is transformed into JSON'
        String json = sut.writeValueAsString( control )

        then: 'JSON has error elements'
        json.contains( 'error' )
        json.contains( 'code' )
        json.contains( 'message' )
    }

    def 'exercise meta-data block'() {

        given: 'a control with only meta-data section in it'
        def control = new MagniControl().with {
            metaDataBlock = new MetaDataBlock( mimeType: 'type/subtype;parameter=one', contentLength: randomInteger() )
            it
        }

        when: 'the object is transformed into JSON'
        String json = sut.writeValueAsString( control )

        then: 'JSON has header elements'
        json.contains( 'meta-data' )
    }

    def 'exercise HAL block'() {

        given: 'a control with only headers section in it'
        def control = new MagniControl().with {
            add( new Link( 'http://api.example.com/', 'create' ) )
            add( new Link( 'http://api.example.com/123', 'read' ) )
            add( new Link( 'http://api.example.com/123', 'self' ) )
            it
        }

        when: 'the object is transformed into JSON'
        String json = sut.writeValueAsString( control )

        then: 'JSON has HAL elements'
        json.contains( 'links' )
        json.contains( 'self' )
        json.contains( 'create' )
        json.contains( 'read' )
    }

    @SuppressWarnings( 'Println' )
    def 'printout entire structure'() {

        given: 'a control with everything in it'
        def control = new MagniControl().with {
            httpCode = HttpStatus.OK.value()
            errorBlock = new ErrorBlock( code: randomInteger(),
                                         message: randomHexString(),
                                         developerMessage: randomHexString() )
            metaDataBlock = new MetaDataBlock( mimeType: 'type/subtype;parameter=one', contentLength: randomInteger() )
            add( new Link( 'http://api.example.com/', 'create' ) )
            add( new Link( 'http://api.example.com/123', 'read' ) )
            add( new Link( 'http://api.example.com/123', 'self' ) )
            it
        }

        when: 'the object is transformed into JSON'
        String json = sut.writeValueAsString( control )

        then: 'JSON is printed'
        println json
    }
}