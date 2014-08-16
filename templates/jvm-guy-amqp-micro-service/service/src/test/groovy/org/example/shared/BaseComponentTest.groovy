package org.example.shared

import org.example.Application
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration

/**
 * Common base class for all component-level tests. Sometimes knows as integration tests,
 * these tests access the file system, network and other external resources but don't test
 * the entire system end-to-end from a user's perspective like a functional acceptance test
 * do.
 */
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class BaseComponentTest extends BaseSpecification {
}
