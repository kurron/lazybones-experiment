package org.example.echo

import com.netflix.hystrix.HystrixCommand
import org.example.shared.resilience.HystrixSettingsBuilder

/**
 * Resource gateway to a MongoDB resource.  Notice that there is no fallback logic.  That is because there isn't
 * anything useful you can return if the document could not be saved.
 */
class SaveEchoDocumentGateway extends HystrixCommand<EchoDocument> {

    /**
     * Manages interactions with the database.
     */
    private final EchoDocumentRepository theRepository

    /**
     * The document we will attempt to save.
     */
    private final EchoDocument theDocument

    SaveEchoDocumentGateway( EchoDocumentRepository aRepository, EchoDocument aDocument) {
        super( HystrixSettingsBuilder.buildUsingDefaults( 'save echo document' ) )
        theDocument = aDocument
        theRepository = aRepository
    }

    @Override
    protected EchoDocument run() throws Exception {
        theRepository.save( theDocument )
    }
}
