/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.annotation;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * Indicates that the result of a method call should not be ignored.
 */
@Target(METHOD)
public @interface CheckReturnValue
{
}