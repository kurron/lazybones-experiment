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
import org.springframework.web.bind.annotation.RestController;

/**
 * Indicates that an annotated class is a "inbound gateway", made popular by Martin Fowler.
 * In our layering, an inbound gateway is any component that moves data from outside of
 * the process into the process. This form of inbound gateway specializes in processing
 * REST requests.
 *
 * <p>This annotation serves as a specialization of {@link org.springframework.web.bind.annotation.RestController @RestController},
 * allowing for implementation classes to be autodetected through classpath scanning.
 */
@Target( {ElementType.TYPE} )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@RestController
public @interface InboundRestGateway
{

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";

}
