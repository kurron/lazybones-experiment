package org.example.echo

import groovy.transform.Canonical
import groovy.transform.InheritConstructors
import org.example.shared.BaseRepository
import org.springframework.stereotype.Repository

/**
 * Implements any custom extensions to the repository.  The name must end in Impl in order for
 * Spring Data MongoDB to find it during startup.
 */
@Repository
@Canonical
@InheritConstructors
class EchoDocumentRepositoryImpl extends BaseRepository implements EchoDocumentRepositoryExtension{
}
