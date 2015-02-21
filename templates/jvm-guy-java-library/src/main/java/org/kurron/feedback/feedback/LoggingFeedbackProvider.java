/*
 * Copyright (c) 2015 Ronald D. Kurr kurr@jvmguy.com
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
package org.kurron.feedback.feedback;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Knows how to take a feedback context and send it off to the logging system.
 */
public class LoggingFeedbackProvider implements FeedbackProvider
{
    /**
     * MDC key holding the message code.
     */
    private static final String MESSAGE_CODE = "message-code";

    /**
     * MDC key holding the service code.
     */
    private static final String SERVICE_CODE = "service-code";

    /**
     * MDC key holding the service instance.
     */
    private static final String SERVICE_INSTANCE = "service-instance";

    /**
     * MDC key holding the realm the service is a member of.
     */
    private static final String REALM = "realm";

    /**
     * Sends the message off to the logging server.
     */
    private final Logger theLogger;

    /**
     * Identifies the 'type' of application this is, typically using its project codename. For example, zisa, magni or thor.
     */
    private final String theServiceCode;

    /**
     * Identifies the instance of a particular 'type' of application (the 3rd instance of magni), typically using its process id.
     */
    private final String theServiceInstance;

    /**
     * Identifies the logical group this service instance belongs to.
     */
    private final String theRealm;

    public LoggingFeedbackProvider( final Logger aLogger, final String aServiceCode, final String aServiceInstance, final String aRealm )
    {
        if ( null == aLogger )
        {
            throw new IllegalArgumentException( "Logger may not be null!" );
        }
        if ( null == aServiceCode )
        {
            throw new IllegalArgumentException( "Service Code may not be null!" );
        }
        if ( null == aServiceInstance )
        {
            throw new IllegalArgumentException( "Service instance may not be null!" );
        }
        if ( null == aRealm )
        {
            throw new IllegalArgumentException( "Realm may not be null!" );
        }
        theLogger = aLogger;
        theServiceCode = aServiceCode;
        theServiceInstance = aServiceInstance;
        theRealm = aRealm;
    }

    @Override
    public void sendFeedback( final FeedbackContext context, final Object... arguments )
    {
        storeValuesInMdc( context );

        try {
            final Marker audienceMarker = MarkerFactory.getMarker( context.getAudience().name() );
            final String formatString = context.getFormatString();
            switch ( context.getFeedbackLevel() )
            {
                case TRACE:
                    if ( theLogger.isTraceEnabled() )
                    {
                        theLogger.trace( audienceMarker, formatString, arguments );
                    }
                    break;
                case DEBUG:
                    if ( theLogger.isDebugEnabled() )
                    {
                        theLogger.debug( audienceMarker, formatString, arguments );
                    }
                    break;
                case INFO:
                    if ( theLogger.isInfoEnabled() )
                    {
                        theLogger.info( audienceMarker, formatString, arguments );
                    }
                    break;
                case WARN:
                    if ( theLogger.isWarnEnabled() )
                    {
                        theLogger.warn( audienceMarker, formatString, arguments );
                    }
                    break;
                case ERROR:
                    if ( theLogger.isErrorEnabled() )
                    {
                        theLogger.error( audienceMarker, formatString, arguments );
                    }
                    break;
                default: throw new IllegalArgumentException( "Invalid feedback level detected" );
            }
        }
        finally {
            clearMessageCode();
            // only clear message code -- other values are fixed for the duration of this process
        }
    }

    /**
     * Stores values in the current thread's MDC store. If a null context is detected, an error is thrown.
     * @param context the context.
     */
    private void storeValuesInMdc( final FeedbackContext context )
    {
        if ( null == context )
        {
            throw new IllegalArgumentException( "Context may not be null!" );
        }
        MDC.put( MESSAGE_CODE, Integer.toString( context.getCode() ) );
        MDC.put( SERVICE_CODE, theServiceCode );
        MDC.put( SERVICE_INSTANCE, theServiceInstance );
        MDC.put( REALM, theRealm );
    }

    @Override
    public void sendFeedback( final FeedbackContext context, final Throwable error )
    {
        storeValuesInMdc( context );
        try {
            final Marker audienceMarker = MarkerFactory.getMarker( context.getAudience().name() );
            final String formatString = context.getFormatString();
            switch ( context.getFeedbackLevel() )
            {
                // TRACE/INFO/DEBUG level issues should never be associated with an error
                case TRACE:
                case DEBUG:
                case INFO: throw new IllegalArgumentException( "Level may not be " + context.getFeedbackLevel().name() );
                case WARN:
                    if ( theLogger.isWarnEnabled() )
                    {
                        theLogger.warn( audienceMarker, formatString, error );
                    }
                    break;
                case ERROR:
                    if ( theLogger.isErrorEnabled() )
                    {
                        theLogger.error( audienceMarker, formatString, error );
                    }
                    break;
                default: throw new IllegalArgumentException( "Invalid feedback level detected" );
            }
        }
        finally {
            clearMessageCode();
            // only clear message code -- other values are fixed for the duration of this process
        }
    }

    private static void clearMessageCode()
    {
        MDC.remove( MESSAGE_CODE );
    }

}
