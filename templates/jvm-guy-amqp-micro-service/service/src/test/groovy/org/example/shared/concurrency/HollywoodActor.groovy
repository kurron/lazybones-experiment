package org.example.shared.concurrency

import groovy.transform.Immutable
import groovyx.gpars.actor.DefaultActor

/**
 * Example of isolated mutability using actors.
 */
@Immutable
class HollywoodActor extends DefaultActor {
    String name

    @Override
    protected void act() {
        def terminationLogic = {
            println "${name} is done acting"
        }

        loop( 3, terminationLogic ) {
            react { role ->
                println "${name} playing the ${role}"
                println "${name} runs in ${Thread.currentThread()}"
            }
        }
    }
}
