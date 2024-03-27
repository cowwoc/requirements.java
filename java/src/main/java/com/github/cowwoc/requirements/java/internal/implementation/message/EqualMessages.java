package com.github.cowwoc.requirements.java.internal.implementation.message;

import com.github.cowwoc.requirements.java.StringMappers;
import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

/**
 * Generates failure messages for equality checks.
 */
public final class EqualMessages
{
	/**
	 * The minimum length of a value that triggers a diff.
	 */
	private static final int MAXIMUM_LENGTH_FOR_VISUAL_COMPARISON = 10;

	private EqualMessages()
	{
	}

	/**
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param nameOfValue      the name of the value
	 * @param value            the value being validated
	 * @param valueIsAvailable true if the value may be used
	 * @param nameOfExpected   (optional) the name of the expected element
	 * @param expected         the expected element
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isEqualTo(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, boolean valueIsAvailable, String nameOfExpected, Object expected)
	{
		// Possible scenarios:
		StringMappers stringMappers = validator.configuration().stringMappers();
		if (!valueIsAvailable)
		{
			// 1. The actual value is not available:
			//
			//    Actual must be equal to 123.
			return new MessageBuilder(scope, validator,
				MessageBuilder.quoteName(nameOfValue) + " must be equal to " + stringMappers.toString(expected) +
				".");
		}
		String expectedAsString = stringMappers.toString(expected);
		String valueAsString = stringMappers.toString(value);

		int lengthOfValue = valueAsString.length();
		int lengthOfExpected = expectedAsString.length();
		boolean expectedValueIsShort = lengthOfExpected < MAXIMUM_LENGTH_FOR_VISUAL_COMPARISON &&
		                               !expectedAsString.contains("\n");
		boolean actualValueIsShort = lengthOfValue < MAXIMUM_LENGTH_FOR_VISUAL_COMPARISON &&
		                             !valueAsString.contains("\n");
		boolean valuesAreShort = expectedValueIsShort && actualValueIsShort;

		if (valuesAreShort)
		{
			if (nameOfExpected != null)
			{
				// 2. The actual and expected values are short, and the name of the expected value is known:
				//
				//    Actual must be equal to Expected.
				//    Expected: 123
				//    Actual  : 456
				return new MessageBuilder(scope, validator,
					MessageBuilder.quoteName(nameOfValue) + " must be equal to " + nameOfExpected + ".").
					putContext(expected, nameOfExpected).
					putContext(value, "Actual");
			}
			// 3. The actual and expected values are short, and the name of the expected value is unknown:
			//
			//    Actual must be equal to 123.
			//    Actual: 456
			return new MessageBuilder(scope, validator,
				MessageBuilder.quoteName(nameOfValue) + " must be equal to " + expectedAsString + ".").
				putContext(value, "Actual");
		}
		if (nameOfExpected == null)
			nameOfExpected = "Expected";

		if (!expectedValueIsShort && actualValueIsShort)
		{
			// 4. The expected value is long, but the actual value is short:
			//
			//    Actual had an unexpected value.
			//    Expected: 123
			//    Actual  : 456
			return new MessageBuilder(scope, validator,
				MessageBuilder.quoteName(nameOfValue) + " had an unexpected value.").
				putContext(expected, nameOfExpected).
				putContext(value, "Actual");
		}

		// 5. Either actual or expected are long:
		//
		//    Actual had an unexpected value.
		//
		//    Expected:    123
		//    Diff    : ---+++
		//    Actual  : 456

		return new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(nameOfValue) + " had an unexpected value.").
			addDiff(nameOfValue, value, nameOfExpected, expected, false);
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param nameOfUnwanted (optional) the name of the unwanted element
	 * @param unwanted       the unwanted element
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotEqualTo(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, String nameOfUnwanted, Object unwanted)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, null,
			"may not be equal to", nameOfUnwanted, unwanted);
	}

	/**
	 * @param first  a value
	 * @param second a value
	 * @return true if one value is null but the other is not
	 */
	public static boolean isMixedNullity(Object first, Object second)
	{
		return (first == null) != (second == null);
	}

	/**
	 * @param first  a value
	 * @param second a value
	 * @return true if both values are null or not null
	 */
	public static boolean isSameNullity(Object first, Object second)
	{
		return (first == null) == (second == null);
	}
}