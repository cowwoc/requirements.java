package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.StringMappers;
import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

import static com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for objects.
 */
public final class ObjectMessages
{
	/**
	 * The minimum length of a value that triggers a diff.
	 */
	public static final int MINIMUM_LENGTH_FOR_DIFF = 10;

	private ObjectMessages()
	{
	}

	/**
	 * @param <T>          the type of the value
	 * @param validator    the validator
	 * @param expectedName the name of the expected object
	 * @param expected     the expected object
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isSameReferenceAs(AbstractValidator<?, T> validator,
		MaybeUndefined<String> expectedName, Object expected)
	{
		return compareValues(validator, "must be the same reference as", expectedName, expected);
	}

	/**
	 * @param <T>          the type of the value
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted object
	 * @param unwanted     the unwanted object
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotSameReferenceAs(AbstractValidator<?, T> validator,
		MaybeUndefined<String> unwantedName, Object unwanted)
	{
		return compareValues(validator, "may not be the same reference as", unwantedName, unwanted);
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @param expected  the expected type
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isInstanceOf(AbstractValidator<?, T> validator, GenericType<?> expected)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			name + " must be an instance of " + expected.getName() + ".");
		validator.ifDefined(value ->
		{
			messageBuilder.withContext(value, name);
			messageBuilder.withContext(GenericType.of(value), name + ".getClass()");
		});
		return messageBuilder;
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @param unwanted  the unwanted type
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotInstanceOf(AbstractValidator<?, T> validator, GenericType<?> unwanted)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			name + " may not be an instance of " + unwanted.getName() + ".");

		validator.ifDefined(value ->
		{
			String classOfValue;
			if (value == null)
				classOfValue = "null";
			else
				classOfValue = value.getClass().getName();
			messageBuilder.withContext(value, name).
				withContext(classOfValue, name + ".getClass()");
		});
		return messageBuilder;
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNull(AbstractValidator<?, T> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must be null.");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotNull(AbstractValidator<?, ?> validator)
	{
		String name = validator.getName();
		return new MessageBuilder(validator, quoteName(name) + " may not be null.");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isEmpty(AbstractValidator<?, T> validator)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must be empty.");
		validator.ifDefined(value -> messageBuilder.withContext(value, name));
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotEmpty(AbstractValidator<?, ?> validator)
	{
		String name = validator.getName();
		return new MessageBuilder(validator, quoteName(name) + " may not be empty.");
	}

	/**
	 * @param <T>          the type of the value
	 * @param validator    the validator
	 * @param relationship a description of the relationship between the actual and expected value (e.g. "must
	 *                     be equal to")
	 * @param expectedName the name of the expected value
	 * @param expected     the expected value
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder compareValues(AbstractValidator<?, T> validator, String relationship,
		MaybeUndefined<String> expectedName, Object expected)
	{
		String actualName = validator.getName();
		Object resolvedActual = validator.getValueOrDefault(null);
		StringMappers stringMappers = validator.configuration().stringMappers();
		if (validator.validationFailed() || unnecessaryDiff(resolvedActual, stringMappers) ||
			unnecessaryDiff(expected, stringMappers))
		{
			// 1. One of the values is short and simple enough to make a diff unnecessary.
			//
			//     "actual" must be equal to "expected".
			//     actual  : 123
			//     expected: 456
			String expectedNameOrValue = expectedName.mapDefined(MessageBuilder::quoteName).
				orSuppliedDefault(() -> stringMappers.toString(expected));

			MessageBuilder messageBuilder = new MessageBuilder(validator,
				quoteName(actualName) + " " + relationship + " " + expectedNameOrValue + ".");

			validator.ifDefined(actualValue -> messageBuilder.withContext(actualValue, actualName));
			expectedName.ifDefined(name -> messageBuilder.withContext(expected, name));
			return messageBuilder;
		}

		// 2. Both values are long and/or complex.
		//
		//    "actual" had an unexpected value.
		//
		//    actual  : 456
		//    diff    : ---+++
		//    expected:    123
		String resolvedExpectedName = expectedName.orDefault("expected");
		return new MessageBuilder(validator, quoteName(actualName) + " had an unexpected value.").
			addDiff(actualName, resolvedActual, resolvedExpectedName, expected);
	}

	/**
	 * @param value         a value
	 * @param stringMappers the configuration used to map contextual values to a String
	 * @return true if the value is short and simple enough to forego a diff
	 */
	public static boolean unnecessaryDiff(Object value, StringMappers stringMappers)
	{
		String valueForDiff = stringMappers.toString(value);
		return valueForDiff.length() < MINIMUM_LENGTH_FOR_DIFF &&
			!valueForDiff.contains("\n");
	}
}