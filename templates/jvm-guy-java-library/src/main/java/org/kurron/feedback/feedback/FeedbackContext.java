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
 * This interface describes a contract for objects that are intended to send feedback through the system, including
 * logged messages and exceptions.
 */
public interface FeedbackContext
{
    /**
     * Unique code for the context, usually a message or error code.
     * @return this context's code.
     */
    int getCode();

    /**
     * The format string that can be understood by the SLF4J logging system. Is used to generate
     * both log messages and messages contained in exceptions.
     * @return this context's SLF4J format string.
     */
    String getFormatString();

    /**
     * What feedback level to use when constructing feedback from this context.
     * @return this context's feedback level.
     */
    FeedbackLevel getFeedbackLevel();

    /**
     * Describes the intended viewing audience for this feedback.
     * @return this context's intended audience.
     */
    Audience getAudience();
}
