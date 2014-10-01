package org.example.shared.concurrency

import groovy.transform.Immutable
import groovyx.gpars.actor.DynamicDispatchActor

import java.security.SecureRandom

@Immutable
class Trader extends DynamicDispatchActor {
    private static final SecureRandom random = new SecureRandom()

    void onMessage( Buy message )
    {
        println "Buying ${message.quantity} shares of ${message.ticker}"
    }

    void onMessage( Lookup message )
    {
        sender.send( random.nextInt( Integer.MAX_VALUE ) )
    }
}
