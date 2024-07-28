package com.github.cowwoc.requirements.java.validator;

import com.github.cowwoc.requirements.java.validator.component.ComparableComponent;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements.java.validator.component.ValidatorComponent;

import java.util.function.Function;

/**
 * Validates the state of a {@code Comparable}.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <T> the type of the value that is being validated
 */
public interface ComparableValidator<T extends Comparable<T>>
	extends ValidatorComponent<ComparableValidator<T>, T>,
	ObjectComponent<ComparableValidator<T>, T>,
	ComparableComponent<ComparableValidator<T>, T>
{
}