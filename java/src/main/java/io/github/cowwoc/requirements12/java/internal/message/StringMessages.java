/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.message;

import io.github.cowwoc.requirements12.java.internal.StringMappers;
import io.github.cowwoc.requirements12.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements12.java.internal.validator.AbstractValidator;

import java.util.regex.Pattern;

import static io.github.cowwoc.requirements12.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for strings.
 */
public final class StringMessages
{
	private StringMessages()
	{
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isBlank(AbstractValidator<?, String> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must be empty or contain only whitespace codepoints.");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotBlank(AbstractValidator<?, String> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not be empty or contain only whitespace codepoints.");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isTrimmed(AbstractValidator<?, String> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not contain leading or trailing whitespace.");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isStripped(AbstractValidator<?, String> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not contain leading or trailing whitespace codepoints.");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param prefix    the value that the string must start with
	 * @return a message for the validation failure
	 */
	public static MessageBuilder startsWith(AbstractValidator<?, String> validator,
		String prefix)
	{
		String name = validator.getName();
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must start with " + stringMappers.toString(prefix) + ".");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param prefix    the value that the string must start with
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotStartWith(AbstractValidator<?, String> validator, String prefix)
	{
		String name = validator.getName();
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not start with " + stringMappers.toString(prefix) + ".");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param suffix    the value that the string must end with
	 * @return a message for the validation failure
	 */
	public static MessageBuilder endsWith(AbstractValidator<?, String> validator, String suffix)
	{
		String name = validator.getName();
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must end with " + stringMappers.toString(suffix) + ".");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param suffix    the value that the string must end with
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotEndWith(AbstractValidator<?, String> validator, String suffix)
	{
		String name = validator.getName();
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not end with " + stringMappers.toString(suffix) + ".");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param expected  the expected value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder contains(AbstractValidator<?, String> validator, String expected)
	{
		String name = validator.getName();
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must contain " + stringMappers.toString(expected) + ".");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param unwanted  the unwanted value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContain(AbstractValidator<?, String> validator, String unwanted)
	{
		String name = validator.getName();
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not contain " + stringMappers.toString(unwanted) + ".");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContainWhitespace(AbstractValidator<?, String> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not contain whitespace characters.");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param regex     the regular expression
	 * @return a message for the validation failure
	 */
	public static MessageBuilder matches(AbstractValidator<?, String> validator, Pattern regex)
	{
		String name = validator.getName();
		StringMappers stringMappers = validator.configuration().stringMappers();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must match the regular expression " +
				stringMappers.toString(regex) + ".");
		String value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}
}