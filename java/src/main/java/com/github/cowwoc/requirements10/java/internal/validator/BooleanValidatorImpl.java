package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.message.BooleanMessages;
import com.github.cowwoc.requirements10.java.validator.BooleanValidator;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

public final class BooleanValidatorImpl extends AbstractObjectValidator<BooleanValidator, Boolean>
	implements BooleanValidator
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
	public BooleanValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Boolean> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public BooleanValidator isTrue()
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
	public BooleanValidator isFalse()
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
}