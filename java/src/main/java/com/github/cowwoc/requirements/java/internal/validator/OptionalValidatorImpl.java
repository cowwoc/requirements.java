/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.internal.message.CollectionMessages;
import com.github.cowwoc.requirements.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.validator.OptionalValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

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
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public OptionalValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Optional<T>> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public OptionalValidator<T> isPresent()
	{
		if (value.isNull())
			onNull();
		switch (value.test(Optional::isPresent))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isNotEmpty(scope, this).toString());
		}
		return this;
	}

	@Override
	public OptionalValidator<T> isEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(Optional::isEmpty))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isEmpty(scope, this).toString());
		}
		return this;
	}

	@Override
	public OptionalValidator<T> contains(Object expected)
	{
		return containsImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public OptionalValidator<T> contains(Object expected, String name)
	{
		requireThatNameIsUnique(name);
		return containsImpl(expected, MaybeUndefined.defined(name));
	}

	private OptionalValidator<T> containsImpl(Object expected, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.equals(Optional.ofNullable(expected))))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsValue(scope, this, name, expected).toString());
		}
		return this;
	}
}