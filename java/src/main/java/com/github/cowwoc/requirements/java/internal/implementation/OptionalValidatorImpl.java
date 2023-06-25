/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.OptionalValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class OptionalValidatorImpl<T> extends AbstractObjectValidator<OptionalValidator<T>, Optional<T>>
	implements OptionalValidator<T>
{
	/**
	 * Creates a new validator as a result of a validation.
	 *
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public OptionalValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator,
		String name, Optional<T> value)
	{
		this(scope, validator.configuration(), name, value, validator.context, validator.failures);
	}

	/**
	 * Creates a new validator.
	 *
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public OptionalValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, Optional<T> value)
	{
		this(scope, configuration, name, value, new HashMap<>(), new ArrayList<>());
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	private OptionalValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		Optional<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	public OptionalValidator<T> isPresent()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be present.").toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.isEmpty())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be present.").toString());
		}
		return this;
	}

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	public OptionalValidator<T> isEmpty()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be empty.").toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.isPresent())
		{
			addIllegalArgumentException(
				new MessageBuilder(scope, this, name + " must be empty.").
					addContext(value, "Actual").
					toString());
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
		return containsImpl(expected, name);
	}

	@SuppressWarnings("OptionalAssignedToNull")
	private OptionalValidator<T> containsImpl(Object expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, null, "must contain", name,
					expected).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!value.equals(Optional.ofNullable(expected)))
		{
			MessageBuilder messageBuilder = ComparableMessages.getExpectedVsActual(scope, this, this.name,
				value, "must contain", name, expected);
			messageBuilder.addContext(value, this.name);
			if (name != null)
				messageBuilder.addContext(expected, name);
			addIllegalArgumentException(messageBuilder.toString());
		}
		return this;
	}
}