/*
 * Copyright (c) 2015 Transparent Language.  All rights reserved.
 */
package com.transparent.asgard.feedback;

/**
 * Describes the intended set of "eye balls" that a feedback message is intended for. Should allow us to filter
 * messages as needed.
 */
public enum Audience
{
    OPERATIONS,
    SUPPORT,
    QA,
    DEVELOPMENT
}
