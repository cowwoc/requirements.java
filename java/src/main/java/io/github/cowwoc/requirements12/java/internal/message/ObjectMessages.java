/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.message;

import io.github.cowwoc.requirements12.java.GenericType;
import io.github.cowwoc.requirements12.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements12.java.internal.validator.AbstractObjectValidator;

import static io.github.cowwoc.requirements12.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for objects.
 */
public final class ObjectMessages
{
	private ObjectMessages()
	{
	}

	/**
	 * @param validator    the validator
	 * @param expectedName the name of the expected object
	 * @param expected     the expected object
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isReferenceEqualToFailed(AbstractObjectValidator<?, ?> validator,
		String expectedName, Object expected)
	{
		return ComparableMessages.compareValues(validator, "must point to the same object as", expectedName,
			expected);
	}

	/**
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted object
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isReferenceNotEqualToFailed(AbstractObjectValidator<?, ?> validator,
		String unwantedName)
	{
		return new MessageBuilder(validator,
			quoteName(validator.getName()) + " may not point to the same object as " + quoteName(unwantedName));
	}

	/**
	 * @param validator the validator
	 * @param expected  the expected type
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isInstanceOfFailed(AbstractObjectValidator<?, ?> validator,
		GenericType<?> expected)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must be an instance of " + expected.getName() + ".");
		Object value = validator.getValueOrDefault(null);
		if (value != null || validator.value.isValid())
		{
			messageBuilder.withContext(value, name);
			if (value != null)
				messageBuilder.withContext(GenericType.of(value), name + ".getClass()");
		}
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param unwanted  the unwanted type
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotInstanceOfFailed(AbstractObjectValidator<?, ?> validator,
		GenericType<?> unwanted)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not be an instance of " + unwanted.getName() + ".");
		Object value = validator.getValueOrDefault(null);
		if (value != null || validator.value.isValid())
		{
			messageBuilder.withContext(value, name).
				withContext(GenericType.of(value), name + ".getClass()");
		}
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNullFailed(AbstractObjectValidator<?, ?> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must be null.");
		Object value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotNullFailed(AbstractObjectValidator<?, ?> validator)
	{
		String name = validator.getName();
		return new MessageBuilder(validator, quoteName(name) + " may not be null.");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isEmptyFailed(AbstractObjectValidator<?, ?> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must be empty.");
		Object value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotEmptyFailed(AbstractObjectValidator<?, ?> validator)
	{
		String name = validator.getName();
		return new MessageBuilder(validator, quoteName(name) + " may not be empty.");
	}
}