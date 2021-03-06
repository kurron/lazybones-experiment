/*
 * Copyright (c) 2017. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kurron.example.inbound

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.web.client.RestOperations

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom

/**
 * An example of how Spring Cloud Stream consumes messages.
 **/
@Slf4j
@EnableBinding( Sink )
@SuppressWarnings( 'DuplicateStringLiteral' )
class MessageConsumer {

    @Autowired
    RestOperations template

    // a complete hack to signal to the test that all messages have been processed
    @Autowired
    CountDownLatch latch

    // Using StreamListener tells Spring to do automatic payload transformation.
    // You cannot get to the raw Message. This is the Spring Cloud Stream mechanism.
    @SuppressWarnings( ['GrMethodMayBeStatic', 'GroovyUnusedDeclaration'] )
    @StreamListener( Sink.INPUT )
    void streamListener( String payload, @Headers Map<String,Object> headers  ) {
        headers.each {
            log.debug( '{} = {}', it.key, it.value )
        }
        log.info( 'Processing via StreamListener {}', payload )
        latch.countDown()

        // just to give Zipkin something else to put in the span
        Thread.sleep( ThreadLocalRandom.current().nextInt( 100, 2000 ) )
        template.getForObject( 'https://jsonplaceholder.typicode.com/users', String )
    }

/*
    // Using ServiceActivator gives you raw access at the cost of automatic transformation.
    // This is the Spring Integration mechanism
    @SuppressWarnings( ['GrMethodMayBeStatic', 'GroovyUnusedDeclaration'] )
    @ServiceActivator( inputChannel=Sink.INPUT )
    void serviceActivator( Message<byte[]> payload, @Headers Map<String,Object> headers  ) {
        headers.each {
            log.debug( '{} = {}', it.key, it.value )
        }
        log.info( 'Processing via ServiceActivator {}', payload )
        latch.countDown()
    }
*/
}
