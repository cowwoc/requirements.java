/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.CollectionMessages;
import com.github.cowwoc.requirements10.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements10.java.validator.OptionalValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class OptionalValidatorImpl<T> extends AbstractObjectValidator<OptionalValidator<T>, Optional<T>>
	implements OptionalValidator<T>
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
	public OptionalValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Optional<T>> value, Map<String, Optional<Object>> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public OptionalValidator<T> isPresent()
	{
		if (value.validationFailed(Optional::isPresent))
		{
			failOnNull();
			addIllegalArgumentException(
				ObjectMessages.isNotEmptyFailed(this).toString());
		}
		return this;
	}

	@Override
	public OptionalValidator<T> isEmpty()
	{
		if (value.validationFailed(Optional::isEmpty))
		{
			failOnNull();
			addIllegalArgumentException(
				ObjectMessages.isEmptyFailed(this).toString());
		}
		return this;
	}

	@Override
	public OptionalValidator<T> contains(Object expected)
	{
		return containsImpl(expected, null);
	}

	@Override
	public OptionalValidator<T> contains(Object expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsImpl(expected, name);
	}

	private OptionalValidator<T> containsImpl(Object expected, String name)
	{
		if (value.validationFailed(v -> v.equals(Optional.ofNullable(expected))))
		{
			failOnNull();
			addIllegalArgumentException(
				CollectionMessages.containsFailed(this, name, expected).toString());
		}
		return this;
	}
}