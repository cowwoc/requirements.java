/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.validator;

import io.github.cowwoc.requirements13.java.validator.component.ArrayComponent;
import io.github.cowwoc.requirements13.java.validator.component.ObjectComponent;
import io.github.cowwoc.requirements13.java.validator.component.ValidatorComponent;

/**
 * Validates the state of an {@code Object[]}.
 *
 * @param <T> the type of the array
 * @param <E> the type of elements in the array
 */
public interface ObjectArrayValidator<T, E> extends
	ValidatorComponent<ObjectArrayValidator<T, E>, T>,
	ObjectComponent<ObjectArrayValidator<T, E>, T>,
	ArrayComponent<ObjectArrayValidator<T, E>, T, E>
{
}