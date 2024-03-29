/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.ArrayValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.extension.ExtensibleCollectionValidator;
import com.github.cowwoc.requirements.java.internal.ArrayValidatorImpl;
import com.github.cowwoc.requirements.java.internal.SizeValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ValidationFailureImpl;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.internal.util.Sets;
import com.github.cowwoc.requirements.java.internal.util.Strings;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Extensible implementation of ExtensibleCollectionValidator.
 *
 * @param <S> the type of validator returned by the methods
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public abstract class AbstractCollectionValidator<S, C extends Collection<E>, E>
	extends AbstractObjectValidator<S, C>
	implements ExtensibleCollectionValidator<S, C, E>
{
	private final Pluralizer pluralizer;

	/**
	 * Creates a AbstractCollectionValidator with existing validation failures.
	 *
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param pluralizer   returns the singular or plural form of an element type
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name}, {@code pluralizer} or
	 *                        {@code failures} are null. If {@code name} is blank.
	 */
	protected AbstractCollectionValidator(ApplicationScope scope, Configuration config, String name, C actual,
		Pluralizer pluralizer, List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name, actual, failures, fatalFailure);
		assert (pluralizer != null) : "pluralizer may not be null";
		this.pluralizer = pluralizer;
	}

	@Override
	public S isEmpty()
	{
		return isEmpty(() -> actual.size(), "Actual.size()");
	}

	@Override
	public S isNotEmpty()
	{
		return isNotEmpty(() -> actual.size());
	}

	@Override
	public S contains(E element)
	{
		return contains(element, o -> actual.contains(o));
	}

	@Override
	public S contains(E element, String name)
	{
		return contains(element, name, o -> actual.contains(o));
	}

	@Override
	public S containsExactly(Collection<E> expected)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		Set<E> expectedAsSet = Sets.fromCollection(expected);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, expectedAsSet);
		if (!missing.isEmpty() || !unwanted.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must contain exactly " + expected + ".").
				addContext("Actual", actual).
				addContext("Missing", missing).
				addContext("Unwanted", unwanted);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S containsExactly(Collection<E> expected, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		Set<E> expectedAsSet = Sets.fromCollection(expected);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, expectedAsSet);
		if (!missing.isEmpty() || !unwanted.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must contain exactly the same " + pluralizer.nameOf(2) + " as " + name + ".").
				addContext("Actual", actual).
				addContext("Expected", expected).
				addContext("Missing", missing).
				addContext("Unwanted", unwanted);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S containsAny(Collection<E> expected)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (Collections.disjoint(actual, expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must contain any " + pluralizer.nameOf(1) + " in " + expected + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S containsAny(Collection<E> expected, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (Collections.disjoint(actual, expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must contain any " + pluralizer.nameOf(1) + " in " + name + ".").
				addContext("Actual", actual).
				addContext("Missing", expected);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S containsAll(Collection<E> expected)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}

		if (!actual.containsAll(expected))
		{
			Set<E> expectedAsSet = Sets.fromCollection(expected);
			Set<E> actualAsSet = Sets.fromCollection(actual);
			Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must contain all " + pluralizer.nameOf(2) + " in " + expected + ".").
				addContext("Actual", actual).
				addContext("Missing", missing);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S containsAll(Collection<E> expected, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (!actual.containsAll(expected))
		{
			Set<E> expectedAsSet = Sets.fromCollection(expected);
			Set<E> actualAsSet = Sets.fromCollection(actual);
			Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must contain all " + pluralizer.nameOf(2) + " in " + name + ".").
				addContext("Actual", actual).
				addContext("Expected", expected).
				addContext("Missing", missing);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S doesNotContain(E element)
	{
		return doesNotContain(element, o -> actual.contains(o));
	}

	@Override
	public S doesNotContain(E element, String name)
	{
		return doesNotContain(element, name, o -> actual.contains(o));
	}

	@Override
	public S doesNotContainExactly(Collection<E> other)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(other, "other").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		Set<E> otherAsSet = Sets.fromCollection(other);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(otherAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, otherAsSet);
		if (missing.isEmpty() && unwanted.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not contain exactly " + other);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S doesNotContainExactly(Collection<E> other, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(other, "other").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		Set<E> otherAsSet = Sets.fromCollection(other);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(otherAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, otherAsSet);

		if (missing.isEmpty() && unwanted.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " may not contain exactly the same " + pluralizer.nameOf(2) + " as " + name + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (!Collections.disjoint(actual, elements))
		{
			Set<E> elementsAsSet = Sets.fromCollection(elements);
			Set<E> actualAsSet = Sets.fromCollection(actual);
			Set<E> unwanted = Sets.intersection(actualAsSet, elementsAsSet);
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not contain any " + pluralizer.nameOf(1) + " in " + elements + ".").
				addContext("Actual", actual).
				addContext("Unwanted", unwanted);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (!Collections.disjoint(actual, elements))
		{
			Set<E> elementsAsSet = Sets.fromCollection(elements);
			Set<E> actualAsSet = Sets.fromCollection(actual);
			Set<E> unwanted = Sets.intersection(actualAsSet, elementsAsSet);
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " may not contain any " + pluralizer.nameOf(1) + " in " + name + ".").
				addContext("Actual", actual).
				addContext(Strings.capitalize(pluralizer.nameOf(2)), elements).
				addContext("Unwanted", unwanted);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (actual.containsAll(elements))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not contain all of " + elements + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (actual.containsAll(elements))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " may not contain all " + pluralizer.nameOf(2) + " in " + name + ".").
				addContext("Actual", actual).
				addContext("Unwanted", elements);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S doesNotContainDuplicates()
	{
		if (fatalFailure)
			return getThis();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (actual instanceof Set)
			return getThis();
		int size = actual.size();
		Set<E> unique = new HashSet<>(size);
		Set<E> duplicates = new HashSet<>(size);
		for (E element : actual)
		{
			if (!unique.add(element))
				duplicates.add(element);
		}
		if (!duplicates.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not contain duplicate " + pluralizer.nameOf(2) + ".").
				addContext("Actual", actual).
				addContext("Duplicates", duplicates);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S doesNotContainMixedNulls()
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(actual, "actual").isNotNull();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}

		int numberOfNulls = 0;
		for (E value : actual)
			if (value == null)
				++numberOfNulls;

		if (numberOfNulls != 0 && numberOfNulls != actual.size())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must contain all nulls, or no nulls.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public SizeValidator size()
	{
		if (fatalFailure)
		{
			return new SizeValidatorImpl(scope, config, name, List.of(), name + ".size()", 0, pluralizer,
				getFailures(), fatalFailure);
		}
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return new SizeValidatorImpl(scope, config, name, List.of(), name + ".size()", 0, pluralizer,
				getFailures(), fatalFailure);
		}
		return new SizeValidatorImpl(scope, config, name, actual, name + ".size()", actual.size(), pluralizer,
			getFailures(), fatalFailure);
	}

	@Override
	public S size(Consumer<SizeValidator> consumer)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(size());
		return getThis();
	}

	@Override
	public ArrayValidator<E[], E> asArray(Class<E> type)
	{
		if (fatalFailure)
		{
			return new ArrayValidatorImpl<>(scope, config, name, null, () -> null, Object::equals,
				array -> false, () -> 0, getFailures(), fatalFailure);
		}
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return new ArrayValidatorImpl<>(scope, config, name, null, () -> null, Object::equals,
				array -> false, () -> 0, getFailures(), fatalFailure);
		}
		@SuppressWarnings("unchecked")
		E[] array = (E[]) Array.newInstance(type, actual.size());
		//noinspection SuspiciousMethodCalls
		Function<Object, Boolean> contains = actual::contains;
		return new ArrayValidatorImpl<>(scope, config, name, actual.toArray(array), () -> List.copyOf(actual),
			Object::equals, contains, actual::size, getFailures(), fatalFailure);
	}

	@Override
	public S asArray(Class<E> type, Consumer<ArrayValidator<E[], E>> consumer)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asArray(type));
		return getThis();
	}
}