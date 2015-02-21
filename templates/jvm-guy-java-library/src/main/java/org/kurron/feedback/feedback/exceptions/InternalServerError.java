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
import org.springframework.http.HttpStatus;

/**
 * Signals that a generic server error occurred, and a 500 (internal server error) should be returned to the client.
 */
public class InternalServerError extends AbstractError
{
    public InternalServerError( final FeedbackContext context, final Object... arguments )
    {
        super( context, arguments );
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getDeveloperMessage()
    {
        return "Something bad happened...";
    }
}
