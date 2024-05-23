package com.github.cowwoc.requirements.java.internal.implementation.message;

import com.github.cowwoc.requirements.java.internal.implementation.AbstractValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Set;

/**
 * Generates failure messages for collections.
 */
public final class CollectionMessages
{
	private CollectionMessages()
	{
	}

	/**
	 * @param scope      the application configuration
	 * @param validator  the validator
	 * @param name       the name of the value
	 * @param value      the value being validated
	 * @param nameOfSize (optional) the name of the method that returns the collection's size (absent if value
	 *                   is absent)
	 * @param size       (optional) the size of the collection (absent if value is absent)
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isEmpty(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value, String nameOfSize, int size)
	{
		return new MessageBuilder(scope, validator, MessageBuilder.quoteName(name) + " must be empty.").
			withContext(value, "Actual").
			withContext(size, nameOfSize);
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotEmpty(ApplicationScope scope, AbstractValidator<?> validator, String name)
	{
		return new MessageBuilder(scope, validator, MessageBuilder.quoteName(name) + " may not be empty.");
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfExpected (optional) the name of the element
	 * @param expected       the expected element
	 * @return a message for the validation failure
	 */
	public static MessageBuilder contains(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfExpected, Object expected)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value, "must contain",
			nameOfExpected, expected);
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfUnwanted the name of the unwanted element
	 * @param unwanted       the unwanted element
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContain(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfUnwanted, Object unwanted)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"may not contain", nameOfUnwanted, unwanted);
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfExpected the name of the collection
	 * @param expected       the collection
	 * @param pluralizer     the type of items in the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsExactly(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfExpected, Object expected, Pluralizer pluralizer)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"must consist of the same " + pluralizer.nameOf(2) + " as", nameOfExpected, expected);
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfExpected the name of the collection
	 * @param expected       the collection
	 * @param pluralizer     the type of items in the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsAny(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfExpected, Object expected, Pluralizer pluralizer)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"must contain any " + pluralizer.nameOf(1) + " in", nameOfExpected, expected);
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfExpected the name of the collection
	 * @param expected       the collection
	 * @param pluralizer     the type of items in the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsAll(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfExpected, Object expected, Pluralizer pluralizer)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"must contain all the " + pluralizer.nameOf(2) + " in", nameOfExpected, expected);
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfUnwanted the name of the collection
	 * @param unwanted       the collection
	 * @param pluralizer     the type of items in the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContainExactly(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfUnwanted, Object unwanted, Pluralizer pluralizer)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"may not consist of the same " + pluralizer.nameOf(2) + " as", nameOfUnwanted,
			unwanted);
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfUnwanted the name of the collection
	 * @param unwanted       the collection
	 * @param pluralizer     the type of items in the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContainAny(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfUnwanted, Object unwanted, Pluralizer pluralizer)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"may not contain any " + pluralizer.nameOf(1) + " in", nameOfUnwanted, unwanted);
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param nameOfValue    the name of the value
	 * @param value          (optional) the value being validated
	 * @param nameOfUnwanted the name of the collection
	 * @param unwanted       the collection
	 * @param pluralizer     the type of items in the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContainAll(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value, String nameOfUnwanted, Object unwanted, Pluralizer pluralizer)
	{
		return ComparableMessages.getExpectedVsActual(scope, validator, nameOfValue, value,
			"may not contain all " + pluralizer.nameOf(2) + " in", nameOfUnwanted, unwanted);
	}

	/**
	 * @param scope       the application configuration
	 * @param validator   the validator
	 * @param nameOfValue the name of the value
	 * @param value       (optional) the value being validated
	 * @param duplicates  the set of duplicate elements
	 * @param pluralizer  the type of items in the value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContainDuplicates(ApplicationScope scope,
		AbstractValidator<?> validator, String nameOfValue, Object value, Set<?> duplicates,
		Pluralizer pluralizer)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			nameOfValue + " may not contain duplicate " + pluralizer.nameOf(2) + ".");
		if (value != null)
		{
			message.withContext(value, "Actual").
				withContext(duplicates, "Duplicates");
		}
		return message;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value being validated
	 * @param sorted    (optional) the sorted value (absent if value is absent)
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isSorted(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Object value, Object sorted)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			MessageBuilder.quoteName(name) + " must be sorted.");
		if (value != null)
		{
			message.withContext(sorted, "Expected").
				withContext(value, "Actual");
		}
		return message;
	}

	/**
	 * @param scope       the application configuration
	 * @param validator   the validator
	 * @param nameOfValue the name of the value
	 * @param value       (optional) the value being validated
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsSameNullity(ApplicationScope scope, AbstractValidator<?> validator,
		String nameOfValue, Object value)
	{
		MessageBuilder message = new MessageBuilder(scope, validator,
			nameOfValue + " must contain all nulls, or no nulls.");
		if (value != null)
			message.withContext(value, "Actual");
		return message;
	}
}