package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.BooleanMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.EqualMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.BooleanValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveBooleanValidator;

import java.util.List;
import java.util.Map;

public final class PrimitiveBooleanValidatorImpl extends AbstractValidator<PrimitiveBooleanValidator>
	implements PrimitiveBooleanValidator
{
	private final boolean value;

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
	public PrimitiveBooleanValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		boolean value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, context, failures);
		this.value = value;
	}

	@Override
	public boolean getValue()
	{
		return value;
	}

	@Override
	public boolean getValueOrDefault(boolean defaultValue)
	{
		if (hasFailed())
			return defaultValue;
		return value;
	}

	@Override
	public PrimitiveBooleanValidatorImpl isTrue()
	{
		if (hasFailed() || !value)
		{
			addIllegalArgumentException(
				BooleanMessages.isTrue(scope, this, name).toString());
		}
		return this;
	}

	@Override
	public PrimitiveBooleanValidatorImpl isFalse()
	{
		if (hasFailed() || value)
		{
			addIllegalArgumentException(
				BooleanMessages.isFalse(scope, this, name).toString());
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
		if (hasFailed())
		{
			addIllegalArgumentException(
				EqualMessages.isEqualTo(scope, this, this.name, null, false, name, expected).toString());
		}
		else if (value != expected)
		{
			addIllegalArgumentException(
				EqualMessages.isEqualTo(scope, this, this.name, value, true, name, expected).toString());
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
		if (hasFailed() || value == unwanted)
		{
			addIllegalArgumentException(
				EqualMessages.isNotEqualTo(scope, this, this.name, name, unwanted).toString());
		}
		return this;
	}

	@Override
	public StringValidatorImpl asString()
	{
		return new StringValidatorImpl(scope, configuration, "String.valueOf(" + name + ")",
			String.valueOf(value), context, failures);
	}

	@Override
	public BooleanValidator asBoxed()
	{
		if (hasFailed())
			return new BooleanValidatorImpl(scope, configuration, name, null, context, failures);
		return new BooleanValidatorImpl(scope, configuration, name, value, context, failures);
	}
}