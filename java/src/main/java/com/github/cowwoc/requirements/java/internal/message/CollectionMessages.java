package com.github.cowwoc.requirements.java.internal.message;

import com.github.cowwoc.requirements.java.internal.util.ObjectAndSize;
import com.github.cowwoc.requirements.java.StringMappers;
import com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.CollectionAndDifference;
import com.github.cowwoc.requirements.java.internal.util.CollectionAndDuplicates;
import com.github.cowwoc.requirements.java.internal.util.Difference;
import com.github.cowwoc.requirements.java.internal.util.ListAndSorted;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.internal.validator.AbstractValidator;

import java.util.Collection;

import static com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for collections.
 */
public final class CollectionMessages
{
	private CollectionMessages()
	{
	}

	/**
	 * @param scope            the application configuration
	 * @param validator        the validator
	 * @param objectName       the name of the object
	 * @param objectAndSize    the object and its size
	 * @param relationship     the relationship between the actual and expected sizes (e.g. "must contain less
	 *                         than")
	 * @param expectedSizeName an expression representing the expected size of the collection
	 * @param expectedSize     the number of elements that should be in the collection
	 * @param pluralizer       the type of items in the collection
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsSize(ApplicationScope scope, AbstractValidator<?, Integer> validator,
		String objectName, MaybeUndefined<ObjectAndSize> objectAndSize, String relationship,
		MaybeUndefined<String> expectedSizeName, int expectedSize, Pluralizer pluralizer)
	{
		assert (expectedSize != 0) : "Invoke ObjectMessages.isEmpty() instead";

		// "actual" must contain exactly expected.size() characters.
		// actual         : "hello world"
		// actual.size()  : 11
		// expected.size(): 15
		String expectedNameOrSize = expectedSizeName.mapDefined(MessageBuilder::quoteName).
			orDefault(String.valueOf(expectedSize));

		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(objectName) + " " + relationship + " " + expectedNameOrSize + " " +
				pluralizer.nameOf(expectedSize) + ".");

		objectAndSize.ifDefined(actualValue -> messageBuilder.
			withContext(actualValue.object(), objectName).
			withContext(actualValue.size(), validator.getName()));
		expectedSizeName.ifDefined(name -> messageBuilder.withContext(expectedSize, name));
		return messageBuilder;
	}

	/**
	 * @param scope             the application configuration
	 * @param validator         the validator
	 * @param collectionName    the name of the collection
	 * @param collectionAndSize the collection and its size
	 * @param minimum           the collection's minimum size
	 * @param minimumInclusive  {@code true} if minimum size is inclusive
	 * @param maximum           the collection's maximum size
	 * @param maximumInclusive  {@code true} if maximum size is inclusive
	 * @param pluralizer        the type of items in the collection
	 */
	public static MessageBuilder sizeIsBetween(ApplicationScope scope, AbstractValidator<?, ?> validator,
		String collectionName, MaybeUndefined<ObjectAndSize> collectionAndSize, int minimum,
		boolean minimumInclusive, int maximum, boolean maximumInclusive, Pluralizer pluralizer)
	{
		assert (maximum >= minimum) : "minimum: " + minimum + ", maximum: " + maximum;
		StringBuilder message = new StringBuilder(quoteName(collectionName));

		StringBuilder bounds = new StringBuilder();
		int inclusiveMinimum;
		int exclusiveMaximum;

		if (minimumInclusive)
		{
			bounds.append('[');
			inclusiveMinimum = minimum;
		}
		else
		{
			bounds.append('(');
			inclusiveMinimum = minimum + 1;
		}
		StringMappers stringMappers = validator.configuration().stringMappers();
		String minimumAsString = stringMappers.toString(minimum);
		String maximumAsString = stringMappers.toString(maximum);
		bounds.append(minimumAsString).append(", ").append(maximumAsString);
		if (maximumInclusive)
		{
			bounds.append(']');
			exclusiveMaximum = maximum - 1;
		}
		else
		{
			bounds.append(')');
			exclusiveMaximum = maximum;
		}

		ObjectAndSize resolved = collectionAndSize.orDefault(null);
		if (resolved == null)
		{
			// The actual value is not available (a previous validation failed)
			//
			//  "actual" must contain [1, 3] elements
			message.append(" must contain ").append(bounds).append(pluralizer.nameOf(2)).append('.');
			return new MessageBuilder(scope, validator, message.toString());
		}

		// actual must contain at least 4 characters.
		// actual         : "hey"
		// actual.length(): 3
		// Bounds         : [4, 6]
		message.append(" must contain ");
		int actualSize = resolved.size();
		if (actualSize < inclusiveMinimum)
			message.append("at least ");
		else if (actualSize >= exclusiveMaximum)
			message.append("at most ");
		else
		{
			throw new AssertionError("Value should have been out of bounds.\n" +
				"actual: " + resolved + "\n" +
				bounds);
		}
		message.append(pluralizer.nameOf(2)).append('.');
		return new MessageBuilder(scope, validator, message.toString()).
			withContext(resolved.object(), collectionName).
			withContext(actualSize, validator.getName()).
			withContext(bounds, "bounds");
	}

	/**
	 * @param <T>          the type of the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param expectedName the name of the expected value
	 * @param expected     the expected value
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder containsValue(ApplicationScope scope, AbstractValidator<?, T> validator,
		MaybeUndefined<String> expectedName, Object expected)
	{
		// "actual" must contain the same value as "expected".
		// actual  : 5
		// expected: 2
		return containsValue(scope, validator, "must contain", expectedName, expected);
	}

	/**
	 * @param <T>          the type of the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted value
	 * @param unwanted     the unwanted value
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder doesNotContainValue(ApplicationScope scope,
		AbstractValidator<?, T> validator,
		MaybeUndefined<String> unwantedName, Object unwanted)
	{
		// "actual" may not contain the same value as "unwanted".
		// actual  : 5
		// unwanted: 2
		return containsValue(scope, validator, "may not contain", unwantedName, unwanted);
	}

	/**
	 * @param <T>          the type of the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param relationship the relationship between the actual and other value (e.g. "must contain")
	 * @param otherName    the name of the other value
	 * @param other        the other value
	 * @return a message for the validation failure
	 */
	private static <T> MessageBuilder containsValue(ApplicationScope scope, AbstractValidator<?, T> validator,
		String relationship, MaybeUndefined<String> otherName, Object other)
	{
		// "actual" must contain the same value as "expected".
		// actual: 5
		// factor: 2
		String otherNameOrValue = otherName.
			mapDefined(name -> "the same value as " + quoteName(name)).
			orDefault(validator.configuration().stringMappers().toString(other));

		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " " + relationship + " " + otherNameOrValue + ".");
		otherName.ifDefined(name -> messageBuilder.withContext(other, name));
		return messageBuilder;
	}

	/**
	 * @param <T>          the type of the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param value        the value being validated, and the difference between the actual and expected values
	 * @param expectedName the name of the collection
	 * @param expected     the collection
	 * @param pluralizer   the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder containsExactly(ApplicationScope scope, AbstractValidator<?, T> validator,
		MaybeUndefined<? extends CollectionAndDifference<T, ?>> value, MaybeUndefined<String> expectedName,
		Object expected, Pluralizer pluralizer)
	{
		// "actual" must consist of the elements [2, 3, 4], regardless of their order.
		//
		// or
		//
		// "actual" must consist of the same elements as "expected", regardless of their order.
		// actual  : [1, 2, 3]
		// expected: [2, 3, 4]
		// missing : [4]
		// unwanted: [1]

		String actualName = validator.getName();
		StringBuilder message = new StringBuilder(quoteName(actualName)).append(" must consist of the ");
		if (expectedName.isDefined())
			message.append("same ");
		message.append(pluralizer.nameOf(2)).append(' ');
		String expectedNameOrValue = expectedName.mapDefined(name -> "as " + quoteName(name)).
			orSuppliedDefault(() -> validator.configuration().stringMappers().toString(expected));
		message.append(expectedNameOrValue).append(", regardless of their order.");

		MessageBuilder messageBuilder = new MessageBuilder(scope, validator, message.toString());
		value.ifDefined(actualValue -> messageBuilder.withContext(actualValue.value(), actualName));
		expectedName.ifDefined(name -> messageBuilder.withContext(expected, name));
		value.ifDefined(actualValue ->
		{
			Difference<?> difference = actualValue.difference();
			messageBuilder.withContext(difference.onlyInOther(), "missing").
				withContext(difference.onlyInActual(), "unwanted");
		});
		return messageBuilder;
	}

	/**
	 * @param <T>          the type of the value
	 * @param <E>          the type of elements in the value
	 * @param <C>          the type of the unwanted value's collection
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param unwantedName the name of the collection
	 * @param unwanted     the collection
	 * @param pluralizer   the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <T, E, C extends Collection<E>> MessageBuilder doesNotContainExactly(ApplicationScope scope,
		AbstractValidator<?, T> validator, MaybeUndefined<String> unwantedName, C unwanted,
		Pluralizer pluralizer)
	{
		// "actual" may not consist of the elements [2, 3, 4], regardless of their order.
		//
		// or
		//
		// "actual" may not consist of the same elements as "expected", regardless of their order.
		// unwanted  : [1, 2, 3]
		String actualName = validator.getName();
		StringBuilder message = new StringBuilder(quoteName(actualName)).append(" may not consist of the ");
		if (unwantedName.isDefined())
			message.append("same ");
		message.append(pluralizer.nameOf(2)).append(' ');
		String unwantedStringNameOrValue = unwantedName.mapDefined(
				name -> "as " + quoteName(name)).
			orSuppliedDefault(() -> validator.configuration().stringMappers().toString(unwanted));
		message.append(unwantedStringNameOrValue).append(", regardless of their order.");

		MessageBuilder messageBuilder = new MessageBuilder(scope, validator, message.toString());
		unwantedName.ifDefined(name -> messageBuilder.withContext(unwanted, name));
		return messageBuilder;
	}

	/**
	 * @param <T>          the type of the value
	 * @param <E>          the type of elements in the value
	 * @param <C>          the type of the expected value's collection
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param value        the value being validated
	 * @param expectedName the name of the expected collection
	 * @param expected     the collection of expected values
	 * @param pluralizer   the type of items in the collections
	 * @return a message for the validation failure
	 */
	public static <T, C extends Collection<E>, E> MessageBuilder containsAny(
		ApplicationScope scope, AbstractValidator<?, T> validator, MaybeUndefined<?> value,
		MaybeUndefined<String> expectedName, C expected, Pluralizer pluralizer)
	{
		// "actual" must contain any of the elements present in "expected".
		// actual  : [1, 2, 3]
		// expected: [2, 3, 4]
		String expectedNameOrValue = expectedName.mapDefined(MessageBuilder::quoteName).
			orSuppliedDefault(() -> "the set " + validator.configuration().stringMappers().toString(expected));

		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " must contain any of the " + pluralizer.nameOf(2) +
				" present in " + expectedNameOrValue + ".");

		value.ifDefined(actualValue -> messageBuilder.withContext(actualValue, actualName));
		expectedName.ifDefined(name -> messageBuilder.withContext(expected, name));
		return messageBuilder;
	}

	/**
	 * @param <T>          the type of the value
	 * @param <C>          the type of the unwanted value's collection
	 * @param <E>          the type of elements in the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param value        the value being validated, and the difference between the actual and unwanted values
	 * @param unwantedName the name of the unwanted collection
	 * @param unwanted     the collection of unwanted elements
	 * @param pluralizer   the type of items in the collections
	 * @return a message for the validation failure
	 */
	public static <T, C extends Collection<E>, E> MessageBuilder doesNotContainAny(
		ApplicationScope scope, AbstractValidator<?, ?> validator,
		MaybeUndefined<CollectionAndDifference<T, E>> value, MaybeUndefined<String> unwantedName, C unwanted,
		Pluralizer pluralizer)
	{
		// "actual" may not contain any of the elements present in "unwanted".
		// actual  : [1, 2, 3]
		// unwanted: [2, 3, 4]
		// elementsToRemove: [2, 3, 4]
		String expectedNameOrValue = unwantedName.mapDefined(MessageBuilder::quoteName).
			orSuppliedDefault(() -> "the set " + validator.configuration().stringMappers().toString(unwanted));

		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " may not contain any of the " + pluralizer.nameOf(2) +
				" present in " + expectedNameOrValue + ".");

		value.ifDefined(actualValue -> messageBuilder.withContext(actualValue, actualName));
		unwantedName.ifDefined(name -> messageBuilder.withContext(unwanted, name));
		value.ifDefined(actualValue -> messageBuilder.
			withContext(actualValue.difference().common(), "elementsToRemove"));
		return messageBuilder;
	}

	/**
	 * @param <T>          the type of the value
	 * @param <C>          the type of the expected value's collection
	 * @param <E>          the type of elements in the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param value        the value being validated, and the difference between the actual and expected values
	 * @param expectedName the name of the expected collection
	 * @param expected     the collection of expected values
	 * @param pluralizer   the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <T, C extends Collection<E>, E> MessageBuilder containsAll(ApplicationScope scope,
		AbstractValidator<?, T> validator, MaybeUndefined<CollectionAndDifference<T, E>> value,
		MaybeUndefined<String> expectedName, C expected, Pluralizer pluralizer)
	{
		// "actual" must contain all the elements present in "expected".
		// actual  : [1, 2, 3]
		// expected: [2, 3, 4]
		// missing : [4]
		String expectedNameOrValue = expectedName.mapDefined(MessageBuilder::quoteName).
			orSuppliedDefault(() -> "the set " + validator.configuration().stringMappers().toString(expected));

		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " must contain all the " + pluralizer.nameOf(2) +
				" present in " + expectedNameOrValue + ".");

		value.ifDefined(actualValue -> messageBuilder.withContext(actualValue.value(), actualName));
		expectedName.ifDefined(name -> messageBuilder.withContext(expected, name));
		value.ifDefined(actualValue -> messageBuilder.
			withContext(actualValue.difference().onlyInOther(), "missing"));
		return messageBuilder;
	}

	/**
	 * @param <T>          the type of the value
	 * @param <C>          the type of the unwanted value's collection
	 * @param <E>          the type of elements in the value
	 * @param scope        the application configuration
	 * @param validator    the validator
	 * @param value        the value being validated, and the difference between the actual and unwanted values
	 * @param unwantedName the name of the unwanted collection
	 * @param unwanted     the collection of unwanted values
	 * @param pluralizer   the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <T, C extends Collection<E>, E> MessageBuilder doesNotContainAll(ApplicationScope scope,
		AbstractValidator<?, T> validator, MaybeUndefined<?> value, MaybeUndefined<String> unwantedName,
		C unwanted, Pluralizer pluralizer)
	{
		// "actual" may not contain some, but not all, the elements present in "unwanted".
		// actual  : [1, 2, 3]
		// unwanted: [2, 3, 4]
		String unwantedNameOrValue = unwantedName.mapDefined(MessageBuilder::quoteName).
			orSuppliedDefault(() -> "the set " + validator.configuration().stringMappers().toString(unwanted));

		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " may contain some, but not all, the " + pluralizer.nameOf(2) +
				" present in " + unwantedNameOrValue + ".");

		value.ifDefined(actualValue -> messageBuilder.withContext(actualValue, actualName));
		unwantedName.ifDefined(name -> messageBuilder.withContext(unwanted, name));
		return messageBuilder;
	}

	/**
	 * @param <T>                the type of the value
	 * @param scope              the application configuration
	 * @param validator          the validator
	 * @param valueAndDuplicates the value being validated
	 * @param pluralizer         the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder doesNotContainDuplicates(ApplicationScope scope,
		AbstractValidator<?, T> validator,
		MaybeUndefined<? extends CollectionAndDuplicates<T, ?>> valueAndDuplicates, Pluralizer pluralizer)
	{
		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " may not contain any duplicate " + pluralizer.nameOf(2) + ".");
		validator.ifDefined(actualValue -> messageBuilder.withContext(actualValue, actualName));
		valueAndDuplicates.ifDefined(value -> messageBuilder.
			withContext(value.value(), actualName).
			withContext(value.duplicates(), "duplicates"));
		return messageBuilder;
	}

	/**
	 * @param scope          the application configuration
	 * @param validator      the validator
	 * @param valueAndSorted the value being validated and its sorted representation
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isSorted(ApplicationScope scope, AbstractValidator<?, ?> validator,
		MaybeUndefined<? extends ListAndSorted<?>> valueAndSorted)
	{
		String actualName = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(scope, validator,
			quoteName(actualName) + " must be sorted.");
		valueAndSorted.ifDefined(value -> messageBuilder.
			withContext(value.value(), actualName).
			withContext(value.sorted(), "expected"));
		return messageBuilder;
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsSameNullity(ApplicationScope scope, AbstractValidator<?, ?> validator)
	{
		String actualName = validator.getName();
		MessageBuilder message = new MessageBuilder(scope, validator,
			actualName + " must contain all nulls, or no nulls.");
		validator.ifDefined(value -> message.withContext(value, actualName));
		return message;
	}
}