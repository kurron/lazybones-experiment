package org.example.shared

import org.example.echo.EchoDocumentRepository
import org.springframework.beans.factory.annotation.Autowired

/**
 * Convenience class for component tests verifying MongoDB operations.
 */
class BaseMongoDbComponentTest extends BaseComponentTest {

    @Autowired
    protected EchoDocumentRepository echoDocumentRepository
}
