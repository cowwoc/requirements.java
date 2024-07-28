/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.GenericType;
import com.github.cowwoc.requirements.java.internal.message.ClassMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.validator.ClassValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

/**
 * @param <T> the type of the class modelled by the {@code Class} object
 */
public final class ClassValidatorImpl<T> extends AbstractObjectValidator<ClassValidator<T>, GenericType<T>>
	implements ClassValidator<T>
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
	public ClassValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<GenericType<T>> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public ClassValidator<T> isPrimitive()
	{
		if (value.isNull())
			onNull();
		switch (value.test(GenericType::isPrimitive))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ClassMessages.isPrimitive(scope, this).toString());
		}
		return this;
	}

	@Override
	public <U> ClassValidator<U> isSupertypeOf(Class<? extends U> type)
	{
		return isSupertypeOf(GenericType.from(type));
	}

	@Override
	public <U> ClassValidator<U> isSupertypeOf(GenericType<? extends U> type)
	{
		scope.getInternalValidators().requireThat(type, "type").isNotNull();
		switch (value.test(value -> value != null && type.isSubtypeOf(value)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ClassMessages.isSupertypeOf(scope, this, type).toString());
		}
		return assumeExpectedType();
	}

	@Override
	public <U> ClassValidator<U> isSubtypeOf(Class<? super U> type)
	{
		return isSubtypeOf(GenericType.from(type));
	}

	@Override
	public <U> ClassValidator<U> isSubtypeOf(GenericType<? super U> type)
	{
		scope.getInternalValidators().requireThat(type, "type").isNotNull();
		switch (value.test(value -> value != null && type.isSupertypeOf(value)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ClassMessages.isSubtypeOf(scope, this, type).toString());
		}
		return assumeExpectedType();
	}
}