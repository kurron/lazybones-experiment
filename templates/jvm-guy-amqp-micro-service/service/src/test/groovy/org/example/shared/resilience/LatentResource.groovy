package org.example.shared.resilience

import groovy.transform.Canonical

/**
 * An example of a slow running integration point.
 */
@Canonical
class LatentResource {
    long latency = 1000

    String fetchResource() {
        Thread.currentThread().sleep( latency )
        'Success!'
    }
}
