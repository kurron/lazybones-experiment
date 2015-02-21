/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;

/**
 * Indicates that an annotated class is a "outbound gateway", made popular by Martin Fowler.
 * In our layering, an outbound gateway is any component that moves data from within the
 * process to outside the process.  Examples, include contacting REST endpoint, databases
 * or the file system.
 *
 * <p>This annotation serves as a specialization of {@link org.springframework.stereotype.Component @Component},
 * allowing for implementation classes to be autodetected through classpath scanning.
 */
@Target( {ElementType.TYPE} )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Service
public @interface OutboundGateway {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";

}
