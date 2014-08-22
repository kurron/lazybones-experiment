package org.example.shell.configuration

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider
import org.springframework.stereotype.Component

@SuppressWarnings( ['GroovyUnusedDeclaration', 'GetterMethodCouldBeProperty'] )
@Component
@Order( Ordered.HIGHEST_PRECEDENCE )
class HistoryFileNameProvider extends DefaultHistoryFileNameProvider {

    String getHistoryFileName() { 'example-micro-service-shell.log' }

}
