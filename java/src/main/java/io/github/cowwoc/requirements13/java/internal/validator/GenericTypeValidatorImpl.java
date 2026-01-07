/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.GenericType;
import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.message.ClassMessages;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.GenericTypeValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public GenericTypeValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<GenericType<T>> value, Map<String, Optional<Object>> context,
		List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public GenericTypeValidator<T> isPrimitive()
	{
		if (value.validationFailed(GenericType::isPrimitive))
		{
			addIllegalArgumentException(
				ClassMessages.isPrimitiveFailed(this).toString());
		}
		return this;
	}

	@Override
	public <U> GenericTypeValidator<U> isSupertypeOf(Class<? extends U> type)
	{
		return isSupertypeOf(GenericType.from(type));
	}

	@Override
	public <U> GenericTypeValidator<U> isSupertypeOf(GenericType<? extends U> subtype)
	{
		scope.getInternalValidators().requireThat(subtype, "type").isNotNull();
		if (value.validationFailed(subtype::isSubtypeOf))
		{
			addIllegalArgumentException(
				ClassMessages.isSupertypeOfFailed(this, subtype).toString());
		}
		return self();
	}

	@Override
	public <U> GenericTypeValidator<U> isSubtypeOf(Class<? super U> supertype)
	{
		return isSubtypeOf(GenericType.from(supertype));
	}

	@Override
	public <U> GenericTypeValidator<U> isSubtypeOf(GenericType<? super U> supertype)
	{
		scope.getInternalValidators().requireThat(supertype, "type").isNotNull();
		if (value.validationFailed(supertype::isSupertypeOf))
		{
			addIllegalArgumentException(
				ClassMessages.isSubtypeOfFailed(this, supertype).toString());
		}
		return self();
	}
}