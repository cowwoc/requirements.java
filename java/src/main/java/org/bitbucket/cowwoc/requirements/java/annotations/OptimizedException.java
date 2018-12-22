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
