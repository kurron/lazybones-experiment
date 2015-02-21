/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Indicates that an annotated class is a "service stub", made popular by Martin Fowler.
 * Beans identified by this annotation are expected to provide "fake" implementations of
 * their production counterparts.  Beans marked by this annotation will only be active
 * when the "use-stubs" profile is active and will supercede any beans of the same type within
 * the Spring context.
 *
 * <p>This annotation serves as a specialization of {@link org.springframework.stereotype.Component @Component},
 * allowing for implementation classes to be autodetected through classpath scanning.
 */
@Target( {ElementType.TYPE} )
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Component
@Profile( "use-stubs" )
@Primary
public @interface ServiceStub
{

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";

}
