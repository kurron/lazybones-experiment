/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni

/**
 * An abstract builder that also provides a random data generator.
 */
abstract class Builder<T> {

    @Delegate
    protected Randomizer randomizer = new Randomizer()

    abstract T build()
}
