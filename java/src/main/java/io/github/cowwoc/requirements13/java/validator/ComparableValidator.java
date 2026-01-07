/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.validator;

import io.github.cowwoc.requirements13.java.validator.component.ComparableComponent;
import io.github.cowwoc.requirements13.java.validator.component.ObjectComponent;
import io.github.cowwoc.requirements13.java.validator.component.ValidatorComponent;

/**
 * Validates the state of a {@code Comparable}.
 *
 * @param <T> the type of the value that is being validated
 */
public interface ComparableValidator<T extends Comparable<T>>
	extends ValidatorComponent<ComparableValidator<T>, T>,
	ObjectComponent<ComparableValidator<T>, T>,
	ComparableComponent<ComparableValidator<T>, T>
{
}