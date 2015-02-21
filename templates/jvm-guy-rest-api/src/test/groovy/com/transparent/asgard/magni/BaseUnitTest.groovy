/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni

import com.transparent.asgard.magni.categories.UnitTests
import org.junit.experimental.categories.Category

/**
 * Base class for unit-level tests.
 */
@Category( UnitTests )
abstract class BaseUnitTest extends BaseTest {

    def setup() { 0 * _._ }
}
