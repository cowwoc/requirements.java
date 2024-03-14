package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.BooleanMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.BooleanValidator;

import java.util.List;
import java.util.Map;

public final class BooleanValidatorImpl extends AbstractObjectValidator<BooleanValidator, Boolean>
	implements BooleanValidator
{
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
	public BooleanValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		Boolean value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public BooleanValidator isTrue()
	{
		if (hasFailed())
			addIllegalArgumentException(BooleanMessages.isFalse(scope, this, name).toString());
		else if (value == null)
			addNullPointerException(ObjectMessages.isNotNull(scope, this, this.name).toString());
		else if (!value)
			addIllegalArgumentException(BooleanMessages.isTrue(scope, this, name).toString());
		return this;
	}

	@Override
	public BooleanValidator isFalse()
	{
		if (hasFailed())
			addIllegalArgumentException(BooleanMessages.isFalse(scope, this, name).toString());
		else if (value == null)
			addNullPointerException(ObjectMessages.isNotNull(scope, this, this.name).toString());
		else if (value)
			addIllegalArgumentException(BooleanMessages.isFalse(scope, this, name).toString());
		return this;
	}
}