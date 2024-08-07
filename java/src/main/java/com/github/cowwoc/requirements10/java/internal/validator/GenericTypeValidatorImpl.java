/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.ClassMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.GenericTypeValidator;

import java.util.List;
import java.util.Map;

/**
 * @param <T> the type of the class modelled by the {@code GenericType} object
 */
public final class GenericTypeValidatorImpl<T>
	extends AbstractObjectValidator<GenericTypeValidator<T>, GenericType<T>>
	implements GenericTypeValidator<T>
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
	public GenericTypeValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<GenericType<T>> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public GenericTypeValidator<T> isPrimitive()
	{
		if (value.isNull())
			onNull();
		switch (value.test(GenericType::isPrimitive))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ClassMessages.isPrimitive(this).toString());
		}
		return this;
	}

	@Override
	public <U> GenericTypeValidator<U> isSupertypeOf(Class<? extends U> type)
	{
		return isSupertypeOf(GenericType.from(type));
	}

	@Override
	public <U> GenericTypeValidator<U> isSupertypeOf(GenericType<? extends U> type)
	{
		scope.getInternalValidators().requireThat(type, "type").isNotNull();
		switch (value.test(value -> value != null && type.isSubtypeOf(value)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ClassMessages.isSupertypeOf(this, type).toString());
		}
		return assumeExpectedType();
	}

	@Override
	public <U> GenericTypeValidator<U> isSubtypeOf(Class<? super U> type)
	{
		return isSubtypeOf(GenericType.from(type));
	}

	@Override
	public <U> GenericTypeValidator<U> isSubtypeOf(GenericType<? super U> type)
	{
		scope.getInternalValidators().requireThat(type, "type").isNotNull();
		switch (value.test(value -> value != null && type.isSupertypeOf(value)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ClassMessages.isSubtypeOf(this, type).toString());
		}
		return assumeExpectedType();
	}
}