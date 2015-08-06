package org.kurron.example.rest

import org.springframework.shell.core.CommandMarker
import org.springframework.shell.core.annotation.CliAvailabilityIndicator
import org.springframework.shell.core.annotation.CliCommand
import org.springframework.shell.core.annotation.CliOption
import org.springframework.stereotype.Component

/**
 * Created by vagrant on 8/6/15.
 */
@Component
class PrintMessageCommand implements CommandMarker {

    @CliAvailabilityIndicator( ["print"] )
    boolean isCommandAvailable() {
        true
    }

    @CliCommand( value = "print", help = "Prints message" )
    public String print( @CliOption( key = ["message"], mandatory = true, help = "help message" ) final String message ) {
        "Message: " + message
    }
}
