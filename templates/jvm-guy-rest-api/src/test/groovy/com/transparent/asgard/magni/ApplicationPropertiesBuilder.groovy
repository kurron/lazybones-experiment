/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni

/**
 * Constructs an ApplicationProperties object with reasonable defaults.
 */
class ApplicationPropertiesBuilder extends Builder<ApplicationProperties> {

    @Override
    ApplicationProperties build() {
        new ApplicationProperties( maxPayloadSize: randomInteger( 129, 256 ), requireCorrelationId: randomBoolean() )
    }
}
