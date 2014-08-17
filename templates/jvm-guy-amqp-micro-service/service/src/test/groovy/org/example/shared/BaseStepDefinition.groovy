package org.example.shared

import org.example.Application
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration

/**
 * Convenience class for step definitions.
 */
@IntegrationTest
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class BaseStepDefinition {
}
