package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.internal.message.BooleanMessages;
import com.github.cowwoc.requirements.java.internal.message.ComparableMessages;
import com.github.cowwoc.requirements.java.validator.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

public final class PrimitiveBooleanValidatorImpl extends AbstractPrimitiveValidator<PrimitiveBooleanValidator, Boolean>
	implements PrimitiveBooleanValidator
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
	public PrimitiveBooleanValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Boolean> value, Map<String, Object> context, List<ValidationFailure> failures)
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
		return value.orDefault(defaultValue);
	}

	@Override
	public PrimitiveBooleanValidator isTrue()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				BooleanMessages.isTrue(scope, this).toString());
		}
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isFalse()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				BooleanMessages.isFalse(scope, this).toString());
		}
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isEqualTo(boolean expected)
	{
		return isEqualToImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveBooleanValidator isEqualTo(boolean expected, String name)
	{
		requireThatNameIsUnique(name);
		return isEqualToImpl(expected, MaybeUndefined.defined(name));
	}

	private PrimitiveBooleanValidator isEqualToImpl(boolean expected, MaybeUndefined<String> name)
	{
		switch (value.test(value -> value != null && value == expected))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ComparableMessages.isEqualTo(scope, this, name, expected).toString());
		}
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isNotEqualTo(boolean unwanted)
	{
		return isNotEqualToImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveBooleanValidator isNotEqualTo(boolean unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, MaybeUndefined.defined(name));
	}

	private PrimitiveBooleanValidator isNotEqualToImpl(boolean unwanted, MaybeUndefined<String> name)
	{
		switch (value.test(value -> value != unwanted))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ComparableMessages.isNotEqualTo(scope, this, name, unwanted).toString());
		}
		return this;
	}
}