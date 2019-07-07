/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

/**
 * Validates the requirements of an {@link Object}.
 *
 * @param <T> the type of the value being validated
 */
public interface ObjectValidator<T> extends ExtensibleObjectValidator<ObjectValidator<T>, T>
{
}
