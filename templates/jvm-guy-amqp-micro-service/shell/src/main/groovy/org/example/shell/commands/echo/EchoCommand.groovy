package org.example.shell.commands.echo

import org.example.shell.shared.BaseCommand
import org.springframework.shell.core.annotation.CliCommand
import org.springframework.shell.core.annotation.CliOption
import org.springframework.stereotype.Component

/**
 * Handles interacting with the echo service.
 */
@SuppressWarnings( ['GrMethodMayBeStatic', 'GroovyUnusedDeclaration'] )
@Component
class EchoCommand extends BaseCommand {

    /**
     * Handle the sending of a message to the service.
     * @param contents the text to send.
     * @return the results returned from the service.
     */
    @SuppressWarnings( 'GroovyUnusedDeclaration' )
    @CliCommand( value = 'send', help = 'Send a message to the micro-service' )
    String send( @CliOption( key = ['message'],
                             mandatory = false,
                             help = 'Contents of the message' ) final String contents ) {
        def request = new EchoRequest( contents ?: hexString() )
        def message =  createMessage( toJsonBytes( request ), request.contentType )
        def reply = sendMessage( message, configuration.queue )
        "Service responded with ${reply}"
    }
}
