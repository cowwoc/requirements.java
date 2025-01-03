package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.internal.StringMappers;
import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

import java.util.Objects;

import static com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for validators.
 */
public final class ValidatorMessages
{
	/**
	 * The minimum length of a value that triggers a diff.
	 */
	public static final int MINIMUM_LENGTH_FOR_DIFF = 10;

	private ValidatorMessages()
	{
	}

	/**
	 * @param <T>        the type of the value
	 * @param validator  the validator
	 * @param constraint the constraint that the value must adhere to (e.g. "must be negative")
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder constraintFailed(AbstractValidator<?, T> validator, String constraint)
	{
		// "actual" must be negative.
		// actual: 5
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " " + constraint + ".");
		T value = validator.getValueOrDefault(null);
		if (value != null)
			messageBuilder.withContext(value, name);
		return messageBuilder;
	}

	/**
	 * @param validator    the validator
	 * @param expectedName the name of the expected value ({@code null} if undefined)
	 * @param expected     the expected value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isEqualToFailed(AbstractValidator<?, ?> validator, String expectedName,
		Object expected)
	{
		StringMappers stringMappers = validator.configuration().stringMappers();
		String name = validator.getName();
		Object value = validator.getValueOrDefault(null);
		if (value == null || diffIsUnnecessary(value, stringMappers) ||
			diffIsUnnecessary(expected, stringMappers))
		{
			// 1. One of the values is short and simple enough to make a diff unnecessary.
			//
			//     "actual" must be equal to "expected".
			//     actual  : 123
			//     expected: 456
			String expectedNameOrValue = validator.getNameOrValue("", expectedName, "", expected);
			MessageBuilder messageBuilder = new MessageBuilder(validator,
				quoteName(name) + " must be equal to " + expectedNameOrValue + ".");

			validator.value.ifValid(v -> messageBuilder.withContext(v, name));
			if (expectedName != null)
				messageBuilder.withContext(expected, expectedName);
			return messageBuilder;
		}

		// 2. Both values are long and/or complex.
		//
		//    "actual" had an unexpected value.
		//
		//    actual  : 456
		//    diff    : ---+++
		//    expected:    123
		String resolvedExpectedName = Objects.requireNonNullElse(expectedName, "expected");
		return new MessageBuilder(validator, quoteName(name) + " had an unexpected value.").
			addDiff(name, value, resolvedExpectedName, expected);
	}

	/**
	 * @param value         a value
	 * @param stringMappers the configuration used to map contextual values to a String
	 * @return true if the value is short and simple enough to forego a diff
	 */
	private static boolean diffIsUnnecessary(Object value, StringMappers stringMappers)
	{
		String valueForDiff = stringMappers.toString(value);
		return valueForDiff.length() < MINIMUM_LENGTH_FOR_DIFF &&
			!valueForDiff.contains("\n");
	}

	/**
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted element ({@code null} if undefined)
	 * @param unwanted     the unwanted element
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotEqualToFailed(AbstractValidator<?, ?> validator, String unwantedName,
		Object unwanted)
	{
		//     "actual" may not be equal to "expected".
		//     actual  : 123
		//     expected: 456
		String unwantedNameOrValue = validator.getNameOrValue("", unwantedName, "", unwanted);
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not be equal to " + unwantedNameOrValue + ".");
		validator.value.ifValid(v -> messageBuilder.withContext(v, name));
		if (unwantedName != null)
			messageBuilder.withContext(unwanted, unwantedName);
		return messageBuilder;
	}
}