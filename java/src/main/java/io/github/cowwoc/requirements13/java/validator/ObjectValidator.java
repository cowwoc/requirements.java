/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.validator;

import io.github.cowwoc.requirements13.java.validator.component.ObjectComponent;
import io.github.cowwoc.requirements13.java.validator.component.ValidatorComponent;

/**
 * Validates the state of an {@link Object}.
 *
 * @param <T> the type of the value that is being validated
 */
public interface ObjectValidator<T> extends
	ValidatorComponent<ObjectValidator<T>, T>,
	ObjectComponent<ObjectValidator<T>, T>
{
}