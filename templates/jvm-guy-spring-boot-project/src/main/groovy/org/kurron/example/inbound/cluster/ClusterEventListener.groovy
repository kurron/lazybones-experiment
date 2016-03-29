package org.kurron.example.inbound.cluster

import groovy.util.logging.Slf4j
import org.kurron.stereotype.InboundGateway
import org.springframework.cloud.cluster.leader.event.AbstractLeaderEvent
import org.springframework.cloud.cluster.leader.event.OnGrantedEvent
import org.springframework.cloud.cluster.leader.event.OnRevokedEvent
import org.springframework.context.ApplicationListener

/**
 * Reacts to changes in leadership election.
 */
@Slf4j
@InboundGateway
class ClusterEventListener implements ApplicationListener<AbstractLeaderEvent> {

    @Override
    void onApplicationEvent( AbstractLeaderEvent event ) {

        switch ( event ) {
            case OnGrantedEvent:
                log.info( 'A new leader has been elected for role {}', event.role )
                break
            case OnRevokedEvent:
                break
                log.info( 'An old leader has been removed for role {}', event.role )
                break
            default:
                log.info( 'Heard an unclassified event of type {}', event.class.name )
                break
        }
    }
}
