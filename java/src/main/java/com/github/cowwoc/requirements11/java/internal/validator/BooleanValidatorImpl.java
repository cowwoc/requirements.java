package com.github.cowwoc.requirements11.java.internal.validator;

import com.github.cowwoc.requirements11.java.ValidationFailure;
import com.github.cowwoc.requirements11.java.internal.Configuration;
import com.github.cowwoc.requirements11.java.internal.message.BooleanMessages;
import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements11.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements11.java.validator.BooleanValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class BooleanValidatorImpl extends AbstractObjectValidator<BooleanValidator, Boolean>
	implements BooleanValidator
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
	public BooleanValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Boolean> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public BooleanValidator isTrue()
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
	public BooleanValidator isFalse()
	{
		if (value.validationFailed(v -> !v))
		{
			failOnNull();
			addIllegalArgumentException(
				BooleanMessages.isFalseFailed(this).toString());
		}
		return this;
	}
}