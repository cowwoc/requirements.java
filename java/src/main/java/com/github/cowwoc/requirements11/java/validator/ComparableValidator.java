package com.github.cowwoc.requirements11.java.validator;

import com.github.cowwoc.requirements11.java.validator.component.ComparableComponent;
import com.github.cowwoc.requirements11.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements11.java.validator.component.ValidatorComponent;

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