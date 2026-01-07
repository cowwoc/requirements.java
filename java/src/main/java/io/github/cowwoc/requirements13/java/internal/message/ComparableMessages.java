/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message;

import io.github.cowwoc.requirements13.java.internal.StringMappers;
import io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements13.java.internal.validator.AbstractValidator;

import static io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for comparable types.
 */
public final class ComparableMessages
{
	private ComparableMessages()
	{
	}

	/**
	 * @param validator        the validator
	 * @param limitName        the name of the value's bound ({@code null} if undefined)
	 * @param maximumExclusive the exclusive upper bound
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isLessThanFailed(AbstractValidator<?, ?> validator, String limitName,
		Object maximumExclusive)
	{
		return compareValues(validator, "must be less than", limitName, maximumExclusive);
	}

	/**
	 * @param validator        the validator
	 * @param limitName        the name of the value's bound ({@code null} if undefined)
	 * @param maximumInclusive the inclusive upper bound
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isLessThanOrEqualToFailed(AbstractValidator<?, ?> validator, String limitName,
		Object maximumInclusive)
	{
		return compareValues(validator, "must be less than or equal to", limitName, maximumInclusive);
	}

	/**
	 * @param validator        the validator
	 * @param limitName        the name of the value's bound ({@code null} if undefined)
	 * @param minimumInclusive the inclusive lower bound
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isGreaterThanOrEqualToFailed(AbstractValidator<?, ?> validator,
		String limitName, Object minimumInclusive)
	{
		return compareValues(validator, "must be greater than or equal to", limitName, minimumInclusive);
	}

	/**
	 * @param validator        the validator
	 * @param limitName        the name of the value's bound ({@code null} if undefined)
	 * @param minimumExclusive the exclusive lower bound
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isGreaterThanFailed(AbstractValidator<?, ?> validator, String limitName,
		Object minimumExclusive)
	{
		return compareValues(validator, "must be greater than", limitName, minimumExclusive);
	}

	/**
	 * @param validator    the validator
	 * @param relationship a description of the relationship between the actual and expected value (e.g. "must
	 *                     be equal to")
	 * @param expectedName the name of the expected value ({@code null} if undefined)
	 * @param expected     the expected value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder compareValues(AbstractValidator<?, ?> validator, String relationship,
		String expectedName, Object expected)
	{
		String actualName = validator.getName();

		//     "actual" must be equal to "expected".
		//     actual  : 123
		//     expected: 456
		String expectedNameOrValue = validator.getNameOrValue("", expectedName, "", expected);

		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(actualName) + " " + relationship + " " + expectedNameOrValue + ".");

		Object value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, actualName);
		if (expectedName != null)
			messageBuilder.withContext(expected, expectedName);
		return messageBuilder;
	}

	/**
	 * @param validator          the validator
	 * @param minimum            the Object representation of the lower limit
	 * @param minimumIsInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum            the Object representation of the upper limit
	 * @param maximumIsInclusive {@code true} if the upper bound of the range is inclusive
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isBetweenFailed(AbstractValidator<?, ?> validator, Object minimum,
		boolean minimumIsInclusive, Object maximum, boolean maximumIsInclusive)
	{
		String name = validator.getName();
		MessageBuilder builder = new MessageBuilder(validator, quoteName(name) + " is out of bounds.");

		Object value = validator.getValueOrDefault(null);
		if (value != null)
			builder.withContext(value, name);

		UnquotedStringValue bounds = getBounds(minimum, minimumIsInclusive, maximum, maximumIsInclusive,
			validator.configuration().stringMappers());
		builder.withContext(bounds, "bounds");
		return builder;
	}

	/**
	 * @param minimum            the Object representation of the lower limit
	 * @param minimumIsInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum            the Object representation of the upper limit
	 * @param maximumIsInclusive {@code true} if the upper bound of the range is inclusive
	 * @param stringMappers      the configuration used to map contextual values to a String
	 * @return a message for the validation failure
	 */
	public static UnquotedStringValue getBounds(Object minimum, boolean minimumIsInclusive, Object maximum,
		boolean maximumIsInclusive, StringMappers stringMappers)
	{
		StringBuilder bounds = new StringBuilder();
		if (minimumIsInclusive)
			bounds.append('[');
		else
			bounds.append('(');
		String minimumAsString = stringMappers.toString(minimum);
		String maximumAsString = stringMappers.toString(maximum);
		bounds.append(minimumAsString).append(", ").append(maximumAsString);
		if (maximumIsInclusive)
			bounds.append(']');
		else
			bounds.append(')');
		return new UnquotedStringValue(bounds.toString());
	}
}