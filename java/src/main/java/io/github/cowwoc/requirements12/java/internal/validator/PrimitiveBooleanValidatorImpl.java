/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.validator;

import io.github.cowwoc.requirements12.java.ValidationFailure;
import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.message.BooleanMessages;
import io.github.cowwoc.requirements12.java.internal.message.ValidatorMessages;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements12.java.validator.PrimitiveBooleanValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class PrimitiveBooleanValidatorImpl extends AbstractPrimitiveValidator<PrimitiveBooleanValidator, Boolean>
	implements PrimitiveBooleanValidator
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
	public PrimitiveBooleanValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Boolean> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public boolean getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public boolean getValueOrDefault(boolean defaultValue)
	{
		return value.or(defaultValue);
	}

	@Override
	public PrimitiveBooleanValidator isTrue()
	{
		if (value.validationFailed(v -> v))
		{
			failOnNull();
			addIllegalArgumentException(
				BooleanMessages.isTrueFailed(this).toString());
		}
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isFalse()
	{
		if (value.validationFailed(v -> !v))
		{
			failOnNull();
			addIllegalArgumentException(
				BooleanMessages.isFalseFailed(this).toString());
		}
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isEqualTo(boolean expected)
	{
		return isEqualToImpl(expected, null);
	}

	@Override
	public PrimitiveBooleanValidator isEqualTo(boolean expected, String name)
	{
		requireThatNameIsUnique(name);
		return isEqualToImpl(expected, name);
	}

	private PrimitiveBooleanValidator isEqualToImpl(boolean expected, String name)
	{
		if (value.validationFailed(v -> v == expected))
		{
			addIllegalArgumentException(
				ValidatorMessages.isEqualToFailed(this, name, expected).toString());
		}
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isNotEqualTo(boolean unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

	@Override
	public PrimitiveBooleanValidator isNotEqualTo(boolean unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, name);
	}

	private PrimitiveBooleanValidator isNotEqualToImpl(boolean unwanted, String name)
	{
		if (value.validationFailed(v -> v != unwanted))
		{
			addIllegalArgumentException(
				ValidatorMessages.isNotEqualToFailed(this, name, unwanted).toString());
		}
		return this;
	}
}