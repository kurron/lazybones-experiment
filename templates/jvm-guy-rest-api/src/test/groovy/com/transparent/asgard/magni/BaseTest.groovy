/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni

import spock.lang.Specification

/**
 * Base class for all Spock tests.
 */
@SuppressWarnings( 'AbstractClassWithoutAbstractMethod' )
abstract class BaseTest extends Specification {

    @Delegate
    protected Randomizer randomizer = new Randomizer()
}
