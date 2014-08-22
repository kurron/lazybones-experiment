package org.example.shell

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.shell.Bootstrap

/**
 * Application driver.
 */
@ComponentScan( ['org.example'] )
@Configuration
class Application {

    /**
     * Starts the entire application.
     * @param args
     */
    static void main( String[] args ) {
        Bootstrap.main( args )
    }

    @Autowired
    private ApplicationProperties properties
}
