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

/**
 * Contract for objects that know how to send feedback via various mechanisms, such as logging.
 */
public interface FeedbackProvider
{
    /**
     * Places a feedback message into the system.
     * @param context which message we want to send.
     * @param arguments any information that should be inserted into the formatted message.
     */
    void sendFeedback( final FeedbackContext context, final Object... arguments );

    /**
     * Places a feedback message into the system.
     * @param context which message we want to send.
     * @param error the causal error you want to embed in the feedback.
     */
    void sendFeedback( final FeedbackContext context, final Throwable error );
}
