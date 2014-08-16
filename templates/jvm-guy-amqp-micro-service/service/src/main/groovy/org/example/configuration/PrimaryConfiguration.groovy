package org.example.configuration

import org.example.ApplicationProperties
import org.example.echo.EchoDocumentRepositoryImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoOperations

/**
 * Spring context configuration that contains the definitions of application's beans.  Be aware
 * that Spring Boot will automatically create bean in addition to those that we define.
 */
@Configuration
@EnableConfigurationProperties( ApplicationProperties )
class PrimaryConfiguration {

    /**
     * The MongoDB template that Spring Boot creates for us.
     */
    @Autowired
    MongoOperations mongoOperations

    @Bean
    EchoDocumentRepositoryImpl echoDocumentRepositoryImpl() {
        new EchoDocumentRepositoryImpl( mongoOperations )
    }
}
