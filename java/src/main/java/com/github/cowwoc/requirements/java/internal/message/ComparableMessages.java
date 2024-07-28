package com.github.cowwoc.requirements.java.internal.message;

import com.github.cowwoc.requirements.java.StringMappers;
import com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements.java.internal.validator.AbstractValidator;

import static com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for comparable types.
 */
public final class ComparableMessages
{
	private ComparableMessages()
	{
	}

	/**
	 * @param <T>          the type of the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param expectedName the name of the expected value
	 * @param expected     the expected value
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isEqualTo(ApplicationScope scope, AbstractValidator<?, T> validator,
		MaybeUndefined<String> expectedName, Object expected)
	{
		return compareValues(scope, validator, "must be equal to", expectedName, expected);
	}

	/**
	 * @param <T>          the type of the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted element
	 * @param unwanted     the unwanted element
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotEqualTo(ApplicationScope scope, AbstractValidator<?, T> validator,
		MaybeUndefined<String> unwantedName, Object unwanted)
	{
		String actualName = validator.getName();
		Object resolvedActual = validator.getValueOrDefault(null);
		//     "actual" may not be equal to "expected".
		//     actual  : 123
		//     expected: 456
		String unwantedNameOrValue = unwantedName.mapDefined(MessageBuilder::quoteName).
			orSuppliedDefault(() -> validator.configuration().stringMappers().toString(unwanted));

		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " may not be equal to " + unwantedNameOrValue + ".");

		if (resolvedActual != null)
			messageBuilder.withContext(resolvedActual, actualName);
		unwantedName.ifDefined(name -> messageBuilder.withContext(unwanted, name));
		return messageBuilder;
	}

	/**
	 * @param <T>              the type of the value
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param limitName        the name of the value's bound
	 * @param maximumExclusive the exclusive upper bound
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isLessThan(ApplicationScope scope, AbstractValidator<?, T> validator,
		MaybeUndefined<String> limitName, Object maximumExclusive)
	{
		return compareValues(scope, validator, "must be less than", limitName, maximumExclusive);
	}

	/**
	 * @param <T>              the type of the value
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param limitName        the name of the value's bound
	 * @param maximumInclusive the inclusive upper bound
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isLessThanOrEqualTo(ApplicationScope scope,
		AbstractValidator<?, T> validator, MaybeUndefined<String> limitName, Object maximumInclusive)
	{
		return compareValues(scope, validator, "must be less than or equal to", limitName, maximumInclusive);
	}

	/**
	 * @param <T>              the type of the value
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param limitName        the name of the value's bound
	 * @param minimumInclusive the inclusive lower bound
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isGreaterThanOrEqualTo(ApplicationScope scope,
		AbstractValidator<?, T> validator, MaybeUndefined<String> limitName, Object minimumInclusive)
	{
		return compareValues(scope, validator, "must be greater than or equal to", limitName,
			minimumInclusive);
	}

	/**
	 * @param <T>              the type of the value
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param limitName        the name of the value's bound
	 * @param minimumExclusive the exclusive lower bound
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isGreaterThan(ApplicationScope scope, AbstractValidator<?, T> validator,
		MaybeUndefined<String> limitName, Object minimumExclusive)
	{
		return compareValues(scope, validator, "must be greater than", limitName, minimumExclusive);
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

		//     "actual" must be equal to "expected".
		//     actual  : 123
		//     expected: 456
		String expectedNameOrValue = expectedName.mapDefined(MessageBuilder::quoteName).
			orSuppliedDefault(() -> validator.configuration().stringMappers().toString(expected));

		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " " + relationship + " " + expectedNameOrValue + ".");

		if (resolvedActual != null)
			messageBuilder.withContext(resolvedActual, actualName);
		expectedName.ifDefined(name -> messageBuilder.withContext(expected, name));
		return messageBuilder;
	}

	/**
	 * @param <T>              the type of the value
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param minimum          the Object representation of the lower limit
	 * @param minimumInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum          the Object representation of the upper limit
	 * @param maximumInclusive {@code true} if the upper bound of the range is inclusive
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isBetween(ApplicationScope scope, AbstractValidator<?, T> validator,
		Object minimum, boolean minimumInclusive, Object maximum, boolean maximumInclusive)
	{
		String actualName = validator.getName();
		MessageBuilder builder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " is out of bounds.");
		validator.ifDefined(value -> builder.withContext(value, actualName));

		StringBuilder bounds = new StringBuilder();
		if (minimumInclusive)
			bounds.append('[');
		else
			bounds.append('(');
		StringMappers stringMappers = validator.configuration().stringMappers();
		String minimumAsString = stringMappers.toString(minimum);
		String maximumAsString = stringMappers.toString(maximum);
		bounds.append(minimumAsString).append(", ").append(maximumAsString);
		if (maximumInclusive)
			bounds.append(']');
		else
			bounds.append(')');
		builder.withContext(bounds, "bounds");
		return builder;
	}
}