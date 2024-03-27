/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation.message;

import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Generates failure messages for booleans.
 */
public final class BooleanMessages
{
	private BooleanMessages()
	{
	}

	/**
	 * Ensures that a value is true.
	 *
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isTrue(ApplicationScope scope, AbstractValidator<?> validator, String name)
	{
		return new MessageBuilder(scope, validator, MessageBuilder.quoteName(name) + " must be true.");
	}

	/**
	 * Ensures that a value is false.
	 *
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @return a failure message for this validation
	 */
	public static MessageBuilder isFalse(ApplicationScope scope, AbstractValidator<?> validator, String name)
	{
		return new MessageBuilder(scope, validator, MessageBuilder.quoteName(name) + " must be false.");
	}
}