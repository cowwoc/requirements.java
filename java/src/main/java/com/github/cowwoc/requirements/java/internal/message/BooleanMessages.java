/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.message;

import com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.validator.AbstractValidator;

/**
 * Generates failure messages for booleans.
 */
public final class BooleanMessages
{
	private BooleanMessages()
	{
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isTrue(ApplicationScope scope, AbstractValidator<?, Boolean> validator)
	{
		return Messages.constraint(scope, validator, "must be true");
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isFalse(ApplicationScope scope, AbstractValidator<?, Boolean> validator)
	{
		return Messages.constraint(scope, validator, "must be false");
	}
}