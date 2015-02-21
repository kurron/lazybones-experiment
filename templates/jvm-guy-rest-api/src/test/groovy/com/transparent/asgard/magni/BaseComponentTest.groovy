/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.magni

import com.transparent.asgard.magni.categories.ComponentTests
import org.junit.experimental.categories.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ContextConfiguration

/**
 * Base class for component-level tests.
 */
@ContextConfiguration( loader = SpringApplicationContextLoader, classes = Application )
@SuppressWarnings( 'AbstractClassWithoutAbstractMethod' )
@WebIntegrationTest( randomPort = true ) // needed because the CustomErrorController will cause the context to not load
@Category( ComponentTests )
abstract class BaseComponentTest extends BaseTest {

    @Autowired
    protected RedisTemplate redisTemplate
}
