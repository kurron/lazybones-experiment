package org.example.echo

import org.example.shared.BaseUnitTest

/**
 * Unit level test of the SaveEchoDocumentGateway object.
 */
class SaveEchoDocumentGatewayUnitTest extends BaseUnitTest {

    def 'verify normal operation'() {
        given: 'a document to save'
        def toSave = new EchoDocument( message:randomHexString() )

        and: 'a test double'
        def repository = Mock( EchoDocumentRepository )
        1 * repository.save( toSave ) >> { EchoDocument document ->
            document.id = randomHexString()
            document.version = randomPositiveInteger( 100 )
            document
        }

        and: 'a subject under test'
        def sut = new SaveEchoDocumentGateway( repository, toSave )

        when: 'message storage is requested'
        EchoDocument saved = sut.run()

        then: 'the document is saved'
        saved.id
        saved.version
        saved.message == toSave.message
    }
}
