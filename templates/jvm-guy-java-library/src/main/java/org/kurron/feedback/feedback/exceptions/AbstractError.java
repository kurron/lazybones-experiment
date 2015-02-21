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
package org.kurron.feedback.feedback.exceptions;

import org.kurron.feedback.feedback.FeedbackContext;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;

/**
 * A common base class for all Magni errors.
 */
public abstract class AbstractError extends RuntimeException
{
    /**
     * Unique error code.
     */
    private final int code;

    protected AbstractError( final FeedbackContext context, final Object... arguments )
    {
        super( MessageFormatter.arrayFormat( context.getFormatString(), arguments ).getMessage() );
        code = context.getCode();
    }

    public int getCode()
    {
        return code;
    }

    /**
     * Returns the HTTP status associated with this error.
     * @return the HTTP status.
     */
    public abstract HttpStatus getHttpStatus();

    /**
     * Returns a more informative error message, geared toward developers.
     * @return the developer message.
     */
    public abstract String getDeveloperMessage();
}
