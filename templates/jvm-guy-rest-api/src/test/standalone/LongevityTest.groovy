/*
 * Copyright (c) 2015. Ronald D. Kurr kurr@jvmguy.com
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


import com.fasterxml.jackson.databind.ObjectMapper
@GrabResolver( name='milestone', root='http://192.168.254.81:81/artifactory/transparent-milestone/' )
@GrabResolver( name='release', root='http://192.168.254.81:81/artifactory/transparent-release/' )
@GrabResolver( name='third-party', root='http://192.168.254.81:81/artifactory/ext-release-local/' )
@Grab( group='org.springframework', module='spring-web', version='4.1.4.RELEASE' )
@Grab( group='com.transparent.asgard', module='magni', version='1.0.46.MILESTONE' )
@Grab( group='lfsappenders', module='lfsappenders', version='4.1.1' )

import org.kurron.example.rest.inbound.CustomHttpHeaders
import org.kurron.example.rest.inbound.HypermediaControl
import org.kurron.example.rest.inbound.HypermediaControl
import java.security.SecureRandom
import java.util.concurrent.ConcurrentLinkedQueue
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.client.AsyncRestTemplate
import org.springframework.web.util.UriComponentsBuilder

/**
 * This is a standalone Groovy test script.  It is expected that the tester will make
 * minor changes to the script and run it via `groovy LongevityTest`.  The first time
 * it is run it can take a while to start as the dependencies are downloaded over the
 * network.  Subsequent runs should be much quicker.
 **/

// this is the maximum payload currently allowed
final int BYTES_IN_A_MEGABYTE = 1024 * 1024

final int CONCURRENT_USERS = Runtime.getRuntime().availableProcessors() * 10

// total number of transactions to be executed
final int TOTAL_WORKLOAD = CONCURRENT_USERS * 100

final int MAX_PAYLOAD_SIZE = 10

random = new SecureRandom()

// use the async form to conserve threads
def internet = new AsyncRestTemplate()
def log = LoggerFactory.getLogger( 'longevity' )
int size = MAX_PAYLOAD_SIZE * BYTES_IN_A_MEGABYTE
def binary = randomByteArray( size )
def customType = new MediaType( 'type', 'sub-type' )

int randomInteger( int floor = 1, int ceiling = Integer.MAX_VALUE ) {
    assert floor < ceiling
    random.nextInt( ceiling - floor + 1 ) + floor
}

byte[] randomByteArray( int size = 256) {
    def buffer = new byte[size]
    random.nextBytes( buffer )
    buffer
}

URI serverUri() {
    UriComponentsBuilder.newInstance()
            .scheme( 'http' )
            .host( '192.168.254.90' )
            .port( 8080 )
            .path( '/' )
            .build()
            .toUri()
}

ObjectMapper getMapper() {
    new Jackson2ObjectMapperBuilder().build()
}

// try and be moderately thread-safe
ConcurrentLinkedQueue<Closure> work = new ConcurrentLinkedQueue<>()

def downloadCallback = [
    onSuccess: { ResponseEntity<byte[]> entity ->
        if ( entity.statusCode == HttpStatus.OK ) {
            log.info( 'Download successful' )
            log.info( "${work.size()} transactions remaining." )
            // start the next iteration
            work.empty ? log.info( 'Test completed' ) : work.remove().call()
        }
        else
        {
            def control = mapper.readValue( entity.body, HypermediaControl )
            log.error "Download failure for Doh!"
            log.error control.toString()
        }
    },
    onFailure: { Throwable t ->
        log.error( 'Problems with the download!', t )
    }
]

def uploadCallback = [
    onSuccess: { ResponseEntity<HypermediaControl> entity ->
        if ( entity.statusCode == HttpStatus.CREATED ){
            log.info( 'Upload successful' )
            def headers = new HttpHeaders()
            headers.set( CustomHttpHeaders.X_CORRELATION_ID, 'Doh!' )
            headers.setAccept( [customType, HypermediaControl.MEDIA_TYPE] )
            log.debug "Downloading ${entity.headers.getLocation()}"
            def downloadEntity = internet.exchange( entity.headers.getLocation(), HttpMethod.GET, new HttpEntity( headers ), byte[] )
            downloadEntity.addCallback( downloadCallback as ListenableFutureCallback<ResponseEntity<byte[]>> )
        }
        else {
            log.error "Upload failure!"
            log.error entity.body.toString()
        }
    },
    onFailure: { Throwable t ->
        log.error( 'Problems with upload!', t )
    }
]

def workload = {
    // simulate 'think time' of the service.
    Thread.sleep( randomInteger( 500, 55000 ) )

    def uploadURI = serverUri()
    def headers = new HttpHeaders()
    headers.set( CustomHttpHeaders.X_CORRELATION_ID, 'Doh!' )
    headers.set( CustomHttpHeaders.X_EXPIRATION_MINUTES, '1' )
    headers.setContentType( customType )
    headers.setAccept( [customType, HypermediaControl.MEDIA_TYPE] )
    def requestEntity = new HttpEntity( binary, headers )
    def uploadEntity = internet.postForEntity( uploadURI, requestEntity, HypermediaControl )
    uploadEntity.addCallback( uploadCallback as ListenableFutureCallback<ResponseEntity<HypermediaControl>> )
}

log.info "Test a ${TOTAL_WORKLOAD} transaction workload using ${CONCURRENT_USERS} concurrent users."

(1..TOTAL_WORKLOAD).each {
   work.add( workload )
}

CONCURRENT_USERS.times {
    work.empty ? 'nothing to do' : work.remove().call()
}


