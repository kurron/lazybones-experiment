/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Spring bean post processor that injects a feedback provider into beans that want it.
 */
public class FeedbackAwareBeanPostProcessor implements BeanPostProcessor
{
    /**
     * Indicates the type of service emitting the messages.
     */
    private final String theServiceCode;

    /**
     * Indicates the instance of the service emitting the messages.
     */
    private final String theServiceInstance;

    /**
     * Indicates the logical group of the service emitting the messages.
     */
    private final String theRealm;

    public FeedbackAwareBeanPostProcessor( final String aServiceCode, final String aServiceInstance, final String aRealm )
    {
        theServiceCode = aServiceCode;
        theServiceInstance = aServiceInstance;
        theRealm = aRealm;
    }

    @Override
    public Object postProcessBeforeInitialization( final Object bean, final String beanName ) throws BeansException
    {
        if ( bean instanceof FeedbackAware ) {
            final FeedbackProvider provider = new LoggingFeedbackProvider( LoggerFactory.getLogger( bean.getClass() ),
                    theServiceCode,
                    theServiceInstance,
                    theRealm );
            ((FeedbackAware) bean).setFeedbackProvider( provider );
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization( final Object bean, final String beanName ) throws BeansException
    {
        return bean;
    }
}
