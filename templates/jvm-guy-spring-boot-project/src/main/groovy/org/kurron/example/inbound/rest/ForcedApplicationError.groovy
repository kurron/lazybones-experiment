/*
 * Copyright (c) 2016. Ronald D. Kurr kurr@jvmguy.com
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
package org.kurron.example.inbound.rest

import groovy.transform.InheritConstructors
import org.kurron.feedback.exceptions.AbstractError
import org.springframework.http.HttpStatus

/**
 * The error is an example of a type that is due to application logic.
 */
@InheritConstructors
class ForcedApplicationError extends AbstractError {

    @Override
    HttpStatus getHttpStatus() {
        HttpStatus.I_AM_A_TEAPOT
    }

    @SuppressWarnings( 'GetterMethodCouldBeProperty' )
    @Override
    String getDeveloperMessage() {
        'The world is ending!'
    }
}
