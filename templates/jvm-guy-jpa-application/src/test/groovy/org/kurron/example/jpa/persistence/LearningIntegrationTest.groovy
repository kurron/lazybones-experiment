package org.kurron.example.jpa.persistence

import org.kurron.example.jpa.Application
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * A test to see how Spring Data JPA and Spring 4 work together.
 */
@ContextConfiguration( loader = SpringApplicationContextLoader, classes = Application )
class LearningIntegrationTest extends Specification {
}
