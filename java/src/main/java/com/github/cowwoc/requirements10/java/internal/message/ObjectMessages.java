package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.StringMappers;
import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
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
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param expectedName the name of the expected object
	 * @param expected     the expected object
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isSameReferenceAs(ApplicationScope scope,
		AbstractValidator<?, T> validator, MaybeUndefined<String> expectedName, Object expected)
	{
		return compareValues(scope, validator, "must be the same reference as",
			expectedName, expected);
	}

	/**
	 * @param <T>          the type of the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted object
	 * @param unwanted     the unwanted object
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotSameReferenceAs(ApplicationScope scope,
		AbstractValidator<?, T> validator, MaybeUndefined<String> unwantedName, Object unwanted)
	{
		return compareValues(scope, validator, "may not be the same reference as",
			unwantedName, unwanted);
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param expected  the expected type
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isInstanceOf(ApplicationScope scope, AbstractValidator<?, T> validator,
		GenericType<?> expected)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			validator.getName() + " must be an instance of " + expected.getName() + ".");
		validator.ifDefined(value ->
		{
			message.withContext(value, validator.getName());
			message.withContext(GenericType.of(value), validator.getName() + ".getClass()");
		});
		return message;
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param unwanted  the unwanted type
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotInstanceOf(ApplicationScope scope, AbstractValidator<?, T> validator,
		GenericType<?> unwanted)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			validator.getName() + " may not be an instance of " + unwanted.getName() + ".");

		validator.ifDefined(value ->
		{
			String classOfValue;
			if (value == null)
				classOfValue = "null";
			else
				classOfValue = value.getClass().getName();
			message.withContext(value, "actual").
				withContext(classOfValue, validator.getName() + ".getClass()");
		});
		return message;
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNull(ApplicationScope scope, AbstractValidator<?, T> validator,
		String name)
	{
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator, quoteName(name) + " must be null.");
		validator.ifDefined(value -> messageBuilder.withContext(value, validator.getName()));
		return messageBuilder;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotNull(ApplicationScope scope, AbstractValidator<?, ?> validator)
	{
		return new MessageBuilder(scope, validator, quoteName(validator.getName()) +
			" may not be null.");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isEmpty(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " must be empty.");
		validator.ifDefined(value -> messageBuilder.withContext(value, "actual"));
		return messageBuilder;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotEmpty(ApplicationScope scope, AbstractValidator<?, ?> validator)
	{
		return new MessageBuilder(scope, validator,
			quoteName(validator.getName()) + " may not be empty.");
	}

	/**
	 * @param <T>          the type of the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param relationship a description of the relationship between the actual and expected value (e.g. "must
	 *                     be equal to")
	 * @param expectedName the name of the expected value
	 * @param expected     the expected value
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder compareValues(ApplicationScope scope, AbstractValidator<?, T> validator,
		String relationship, MaybeUndefined<String> expectedName, Object expected)
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

			MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
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
		return new MessageBuilder(scope, validator,
			quoteName(actualName) + " had an unexpected value.").
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