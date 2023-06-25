/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.ClassValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T> the type of the class
 */
public final class ClassValidatorImpl<T> extends AbstractObjectValidator<ClassValidator<T>, Class<T>>
	implements ClassValidator<T>
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
	public ClassValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Class<T> value)
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
	public ClassValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		Class<T> value)
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
	private ClassValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		Class<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public ClassValidator<T> isSupertypeOf(Class<?> type)
	{
		scope.getInternalValidator().requireThat(type, "type").isNotNull();

		if (hasFailed())
			addIllegalArgumentException(name + " must be a supertype of " + type + ".");
		else if (value == null || !value.isAssignableFrom(type))
		{
			MessageBuilder message = new MessageBuilder(scope, this,
				name + " must be a supertype of " + type + ".").
				addContext(value, "Actual");
			addIllegalArgumentException(message.toString());
		}
		return this;
	}

	@Override
	public ClassValidator<T> isSubtypeOf(Class<?> type)
	{
		scope.getInternalValidator().requireThat(type, "type").isNotNull();

		if (hasFailed())
		{
			MessageBuilder message = new MessageBuilder(scope, this,
				name + " must be a subtype of " + type + ".").
				addContext(value, "Actual");
			addIllegalArgumentException(message.toString());
		}
		else if (value == null || !type.isAssignableFrom(value))
		{
			MessageBuilder message = new MessageBuilder(scope, this,
				name + " must be a subtype of " + type + ".").
				addContext(value, "Actual");
			addIllegalArgumentException(message.toString());
		}
		return this;
	}
}