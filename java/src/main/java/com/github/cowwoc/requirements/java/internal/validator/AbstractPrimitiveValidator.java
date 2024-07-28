package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

/**
 * Validates the state of a primitive value, recording failures without throwing an exception.
 *
 * @param <S> the type of validator that the methods should return
 * @param <T> the type of the value that is being validated
 */
public abstract class AbstractPrimitiveValidator<S, T> extends AbstractValidator<S, T>
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	protected AbstractPrimitiveValidator(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	protected void onNull()
	{
		throw new AssertionError(value);
	}
}