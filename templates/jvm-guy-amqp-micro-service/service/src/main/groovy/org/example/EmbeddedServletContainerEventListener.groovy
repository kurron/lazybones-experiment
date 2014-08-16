package org.example

import groovy.util.logging.Slf4j
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent
import org.springframework.context.ApplicationListener

/**
 * Listens to container events so we can pull out the HTTP port.
 */
@Slf4j
class EmbeddedServletContainerEventListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    @Override
    void onApplicationEvent( EmbeddedServletContainerInitializedEvent event ) {
        log.info( 'Micro-Service is listening on port {}', event.embeddedServletContainer.port )
    }
}
