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
 * Signals that a required Content-Length header was not set and a status code of 411 (length required) should be
 * returned to the client.
 */
public class LengthRequiredError extends AbstractError
{
    public LengthRequiredError( final FeedbackContext context, final Object... arguments )
    {
        super( context, arguments );
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.LENGTH_REQUIRED;
    }

    @Override
    public String getDeveloperMessage()
    {
        return "It looks like you forgot to set the Content-Length header";
    }
}
