/*
 * Copyright (c) 2018 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates that an exception has been optimized for use with this library.
 */
@Target(value = TYPE)
@Retention(value = RUNTIME)
public @interface OptimizedException
{
}
