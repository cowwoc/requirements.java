/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */

package io.github.cowwoc.requirements13.java.internal.message;

import io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements13.java.internal.util.Difference;
import io.github.cowwoc.requirements13.java.internal.util.Pluralizer;
import io.github.cowwoc.requirements13.java.internal.validator.AbstractObjectValidator;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Generates failure messages for arrays or collections.
 */
public final class CollectionMessages
{
	private CollectionMessages()
	{
	}

	/**
	 * @param validator        the collection's validator
	 * @param actualSizeName   the name of the collection's size
	 * @param actualSize       the collection's size ({@code null} if undefined)
	 * @param relationship     the relationship between the actual and expected sizes of the collection (e.g.
	 *                         "must contain less than")
	 * @param expectedSizeName an expression representing the expected size of the collection ({@code null} if
	 *                         undefined)
	 * @param expectedSize     the number of elements that should be in the collection
	 * @param pluralizer       the type of items in the collection
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsSizeFailed(AbstractObjectValidator<?, ?> validator,
		String actualSizeName, Integer actualSize, String relationship, String expectedSizeName,
		int expectedSize, Pluralizer pluralizer)
	{
		// "actual" must contain exactly expected.size() characters.
		// actual         : "hello world"
		// actual.size()  : 11
		// expected.size(): 15
		String expectedNameOrSize = validator.getNameOrValue("", expectedSizeName, "", expectedSize);
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " " + relationship + " " + expectedNameOrSize + " " +
				pluralizer.nameOf(expectedSize, expectedSizeName) + ".");

		validator.value.nullToInvalid().ifValid(v ->
			messageBuilder.withContext(v, name));
		if (actualSize != null)
			messageBuilder.withContext(actualSize, actualSizeName);
		if (expectedSizeName != null)
			messageBuilder.withContext(expectedSize, expectedSizeName);
		return messageBuilder;
	}

	/**
	 * @param validator          the collection's validator
	 * @param actualSizeName     the name of the collection's size
	 * @param actualSize         the collection's size ({@code null} if undefined)
	 * @param minimum            the collection's minimum size
	 * @param minimumIsInclusive {@code true} if minimum size is inclusive
	 * @param maximum            the collection's maximum size
	 * @param maximumIsInclusive {@code true} if maximum size is inclusive
	 * @param pluralizer         the type of items in the collection
	 */
	public static MessageBuilder sizeIsBetweenFailed(AbstractObjectValidator<?, ?> validator,
		String actualSizeName, Integer actualSize, int minimum, boolean minimumIsInclusive,
		int maximum, boolean maximumIsInclusive, Pluralizer pluralizer)
	{
		assert maximum >= minimum : "minimum: " + minimum + ", maximum: " + maximum;
		UnquotedStringValue bounds = ComparableMessages.getBounds(minimum, minimumIsInclusive, maximum,
			maximumIsInclusive, validator.configuration().stringMappers());

		String name = validator.getName();
		StringBuilder message = new StringBuilder(quoteName(name));
		if (actualSize == null)
		{
			// The size is undefined (e.g. the collection is null)
			//
			//  "actual" must contain [1, 3] elements
			message.append(" must contain ").append(bounds).append(' ').
				append(pluralizer.nameOf(2, null)).append('.');
			return new MessageBuilder(validator, message.toString());
		}

		// actual must contain at least 4 characters.
		// actual         : "hey"
		// actual.length(): 3
		// Bounds         : [4, 6]
		int inclusiveMinimum;
		if (minimumIsInclusive)
			inclusiveMinimum = minimum;
		else
			inclusiveMinimum = minimum + 1;

		int exclusiveMaximum;
		if (maximumIsInclusive)
			exclusiveMaximum = maximum - 1;
		else
			exclusiveMaximum = maximum;

		message.append(" must contain ");
		if (actualSize < inclusiveMinimum)
			message.append("at least ");
		else if (actualSize >= exclusiveMaximum)
			message.append("at most ");
		else
		{
			throw new AssertionError("Value should have been out of bounds.\n" +
				"actual: " + actualSize + "\n" +
				"bounds: " + bounds);
		}
		message.append(pluralizer.nameOf(2, null)).append('.');
		return new MessageBuilder(validator, message.toString()).
			withContext(validator.getValue(), name).
			withContext(actualSize, actualSizeName).
			withContext(bounds, "bounds");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isEmptyFailed(AbstractObjectValidator<?, ?> validator)
	{
		return ObjectMessages.isEmptyFailed(validator);
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotEmptyFailed(AbstractObjectValidator<?, ?> validator)
	{
		return ObjectMessages.isNotEmptyFailed(validator);
	}

	/**
	 * @param validator    the validator
	 * @param expectedName the name of the expected value ({@code null} if undefined)
	 * @param expected     the expected value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder containsFailed(AbstractObjectValidator<?, ?> validator, String expectedName,
		Object expected)
	{
		// "actual" must contain the same value as "expected".
		// actual  : 5
		// expected: 2
		return containsFailedImpl(validator, "must contain", expectedName, expected);
	}

	/**
	 * @param validator    the validator
	 * @param relationship the relationship between the actual and other value (e.g. "must contain")
	 * @param otherName    the name of the other value ({@code null} if undefined)
	 * @param other        the other value
	 * @return a message for the validation failure
	 */
	private static MessageBuilder containsFailedImpl(AbstractObjectValidator<?, ?> validator,
		String relationship, String otherName, Object other)
	{
		// "actual" must contain the same value as "other".
		// actual: 5
		// other : 2
		String name = validator.getName();
		String otherNameOrValue = validator.getNameOrValue("the same value as ", otherName, "", other);
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " " + relationship + " " + otherNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (otherName != null)
			messageBuilder.withContext(other, otherName);
		return messageBuilder;
	}

	/**
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted value ({@code null} if undefined)
	 * @param unwanted     the unwanted value
	 * @return a message for the validation failure
	 */
	public static MessageBuilder doesNotContainFailed(AbstractObjectValidator<?, ?> validator,
		String unwantedName, Object unwanted)
	{
		// "actual" may not contain the same value as "unwanted".
		// actual  : 5
		// unwanted: 2
		return containsFailedImpl(validator, "may not contain", unwantedName, unwanted);
	}

	/**
	 * @param <C>          the type of the expected value's collection
	 * @param <E>          the type of elements in the value
	 * @param validator    the validator
	 * @param expectedName the name of the expected collection ({@code null} if undefined)
	 * @param expected     the collection of expected values
	 * @param pluralizer   the type of items in the collections
	 * @return a message for the validation failure
	 */
	public static <C extends Collection<E>, E> MessageBuilder containsAnyFailed(
		AbstractObjectValidator<?, ?> validator, String expectedName, C expected, Pluralizer pluralizer)
	{
		// "actual" must contain any of the elements present in "expected".
		// actual  : [1, 2, 3]
		// expected: [2, 3, 4]
		String expectedNameOrValue = validator.getNameOrValue("", expectedName, "the set ", expected);

		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must contain any of the " + pluralizer.nameOf(2, null) +
				" present in " + expectedNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (expectedName != null)
			messageBuilder.withContext(expected, expectedName);
		return messageBuilder;
	}

	/**
	 * @param <C>          the type of the unwanted value's collection
	 * @param <E>          the type of elements in the value
	 * @param validator    the validator
	 * @param difference   the difference between the actual and unwanted values ({@code null} if undefined)
	 * @param unwantedName the name of the unwanted collection ({@code null} if undefined)
	 * @param unwanted     the collection of unwanted elements
	 * @param pluralizer   the type of items in the collections
	 * @return a message for the validation failure
	 */
	public static <C extends Collection<E>, E> MessageBuilder doesNotContainAnyFailed(
		AbstractObjectValidator<?, ?> validator, Difference<E> difference, String unwantedName, C unwanted,
		Pluralizer pluralizer)
	{
		// "actual" may not contain any of the elements present in "unwanted".
		// actual  : [1, 2, 3]
		// unwanted: [2, 3, 4]
		// elementsToRemove: [2, 3, 4]
		String unwantedNameOrValue = validator.getNameOrValue("", unwantedName, "the set ", unwanted);

		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not contain any of the " + pluralizer.nameOf(2, null) +
				" present in " + unwantedNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (unwantedName != null)
			messageBuilder.withContext(unwanted, unwantedName);
		if (difference != null)
			messageBuilder.withContext(difference.common(), "elementsToRemove");
		return messageBuilder;
	}

	/**
	 * @param <E>          the type of elements in the value
	 * @param validator    the validator
	 * @param difference   the difference between the actual and expected values ({@code null} if undefined)
	 * @param expectedName the name of the collection ({@code null} if undefined)
	 * @param expected     the collection
	 * @param pluralizer   the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <E> MessageBuilder containsExactlyFailed(AbstractObjectValidator<?, ?> validator,
		Difference<E> difference, String expectedName, Object expected, Pluralizer pluralizer)
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

		String name = validator.getName();
		StringBuilder message = new StringBuilder(quoteName(name)).append(" must consist of the ");
		if (expectedName != null)
			message.append("same ");
		message.append(pluralizer.nameOf(2, null)).append(' ');

		String expectedNameOrValue = validator.getNameOrValue("as ", expectedName, "", expected);
		message.append(expectedNameOrValue).append(", regardless of their order.");

		MessageBuilder messageBuilder = new MessageBuilder(validator, message.toString());
		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (expectedName != null)
			messageBuilder.withContext(expected, expectedName);
		if (difference != null)
			messageBuilder.withContext(difference.onlyInOther(), "missing").
				withContext(difference.onlyInActual(), "unwanted");
		return messageBuilder;
	}

	/**
	 * @param <C>          the type of the unwanted value's collection
	 * @param <E>          the type of elements in the value
	 * @param validator    the validator
	 * @param unwantedName the name of the collection ({@code null} if undefined)
	 * @param unwanted     the collection
	 * @param pluralizer   the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <C extends Collection<E>, E> MessageBuilder doesNotContainExactlyFailed(
		AbstractObjectValidator<?, ?> validator, String unwantedName, C unwanted, Pluralizer pluralizer)
	{
		// "actual" may not consist of the elements [2, 3, 4], regardless of their order.
		//
		// or
		//
		// "actual" may not consist of the same elements as "expected", regardless of their order.
		// unwanted  : [1, 2, 3]
		StringBuilder message = new StringBuilder(quoteName(validator.getName())).
			append(" may not consist of the ");
		if (unwantedName != null)
			message.append("same ");
		message.append(pluralizer.nameOf(2, null)).append(' ');
		String unwantedNameOrValue = validator.getNameOrValue("as ", unwantedName, "", unwanted);
		message.append(unwantedNameOrValue).append(", regardless of their order.");

		MessageBuilder messageBuilder = new MessageBuilder(validator, message.toString());
		if (unwantedName != null)
			messageBuilder.withContext(unwanted, unwantedName);
		return messageBuilder;
	}

	/**
	 * @param <C>          the type of the expected value's collection
	 * @param <E>          the type of elements in the value
	 * @param validator    the validator
	 * @param difference   the difference between the actual and expected values ({@code null} if undefined)
	 * @param expectedName the name of the expected collection ({@code null} if undefined)
	 * @param expected     the collection of expected values
	 * @param pluralizer   the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <C extends Collection<E>, E> MessageBuilder containsAllFailed(
		AbstractObjectValidator<?, ?> validator, Difference<E> difference, String expectedName, C expected,
		Pluralizer pluralizer)
	{
		// "actual" must contain all the elements present in "expected".
		// actual  : [1, 2, 3]
		// expected: [2, 3, 4]
		// missing : [4]
		String expectedNameOrValue = validator.getNameOrValue("", expectedName, "the set ", expected);

		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " must contain all the " + pluralizer.nameOf(2, null) + " present in " +
				expectedNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (expectedName != null)
			messageBuilder.withContext(expected, expectedName);
		if (difference != null)
			messageBuilder.withContext(difference.onlyInOther(), "missing");
		return messageBuilder;
	}

	/**
	 * @param <C>          the type of the unwanted value's collection
	 * @param <E>          the type of elements in the value
	 * @param validator    the validator
	 * @param unwantedName the name of the unwanted collection ({@code null} if undefined)
	 * @param unwanted     the collection of unwanted values
	 * @param pluralizer   the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <C extends Collection<E>, E> MessageBuilder doesNotContainAllFailed(
		AbstractObjectValidator<?, ?> validator, String unwantedName, C unwanted, Pluralizer pluralizer)
	{
		// "actual" may contain some, but not all, the elements present in "unwanted".
		// actual  : [1, 2, 3]
		// unwanted: [2, 3, 4]
		String unwantedNameOrValue = validator.getNameOrValue("", unwantedName, "the set ", unwanted);
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may contain some, but not all, the " + pluralizer.nameOf(2, null) +
				" present in " + unwantedNameOrValue + ".");

		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (unwantedName != null)
			messageBuilder.withContext(unwanted, unwantedName);
		return messageBuilder;
	}

	/**
	 * @param <E>        the type of elements in the value
	 * @param validator  the validator
	 * @param duplicates the duplicate values in the value being validated ({@code null} if undefined)
	 * @param pluralizer the type of items in the value
	 * @return a message for the validation failure
	 */
	public static <E> MessageBuilder doesNotContainDuplicatesFailed(AbstractObjectValidator<?, ?> validator,
		Set<E> duplicates, Pluralizer pluralizer)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator,
			quoteName(name) + " may not contain any duplicate " + pluralizer.nameOf(2, null) + ".");
		if (duplicates != null)
			messageBuilder.withContext(duplicates, "duplicates");
		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		return messageBuilder;
	}

	/**
	 * @param validator the validator
	 * @param sorted    the sorted representation of the value being validated ({@code null} if undefined)
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isSortedFailed(AbstractObjectValidator<?, ?> validator,
		List<?> sorted)
	{
		String name = validator.getName();
		MessageBuilder messageBuilder = new MessageBuilder(validator, quoteName(name) + " must be sorted.");
		validator.value.nullToInvalid().ifValid(v -> messageBuilder.withContext(v, name));
		if (sorted != null)
			messageBuilder.withContext(sorted, "expected");
		return messageBuilder;
	}
}