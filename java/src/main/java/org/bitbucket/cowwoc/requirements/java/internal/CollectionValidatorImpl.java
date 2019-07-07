/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayValidator;
import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;
import org.bitbucket.cowwoc.requirements.java.internal.util.Sets;
import org.bitbucket.cowwoc.requirements.java.internal.util.Strings;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Default implementation of {@code CollectionValidator}.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public final class CollectionValidatorImpl<C extends Collection<E>, E>
	extends AbstractObjectValidator<CollectionValidator<C, E>, C>
	implements CollectionValidator<C, E>
{
	private final Pluralizer pluralizer;

	/**
	 * @param scope      the application configuration
	 * @param config     the instance configuration
	 * @param name       the name of the value
	 * @param actual     the actual value
	 * @param pluralizer returns the singular or plural form of an element type
	 * @param failures   the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name}, {@code pluralizer} or
	 *                        {@code failures} are null. If {@code name} is empty.
	 */
	public CollectionValidatorImpl(ApplicationScope scope, Configuration config, String name, C actual,
	                               Pluralizer pluralizer, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
		assert (pluralizer != null) : "pluralizer may not be null";
		this.pluralizer = pluralizer;
	}

	@Override
	protected CollectionValidator<C, E> getThis()
	{
		return this;
	}

	@Override
	protected CollectionValidator<C, E> getNoOp()
	{
		return new CollectionValidatorNoOp<>(failures);
	}

	@Override
	public CollectionValidator<C, E> isEmpty()
	{
		if (!actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> isNotEmpty()
	{
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be empty");
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> contains(E element)
	{
		if (!actual.contains(element))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must contain " + element + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> contains(E element, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!actual.contains(element))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must contain " + name + ".").
				addContext("Actual", actual).
				addContext("Missing", element);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> containsExactly(Collection<E> expected)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		Set<E> expectedAsSet = Sets.fromCollection(expected);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, expectedAsSet);

		if (!missing.isEmpty() || !unwanted.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must contain exactly " + expected + ".").
				addContext("Actual", actual).
				addContext("Missing", missing).
				addContext("Unwanted", unwanted);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> containsExactly(Collection<E> expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<E> expectedAsSet = Sets.fromCollection(expected);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, expectedAsSet);

		if (!missing.isEmpty() || !unwanted.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must contain exactly the same " + pluralizer.nameOf(2) + " as " + name + ".").
				addContext("Actual", actual).
				addContext("Expected", expected).
				addContext("Missing", missing).
				addContext("Unwanted", unwanted);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> containsAny(Collection<E> expected)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();

		if (!actualContainsAny(expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must contain any " + pluralizer.nameOf(1) + " in " + expected + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	/**
	 * @param elements a collection of elements
	 * @return true if actual value contains any of {@code elements}
	 */
	private boolean actualContainsAny(Collection<E> elements)
	{
		for (E element : elements)
			if (actual.contains(element))
				return true;
		return false;
	}

	@Override
	public CollectionValidator<C, E> containsAny(Collection<E> expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (!actualContainsAny(expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must contain any " + pluralizer.nameOf(1) + " in " + name + ".").
				addContext("Actual", actual).
				addContext("Missing", expected);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> containsAll(Collection<E> expected)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();

		if (!actual.containsAll(expected))
		{
			Set<E> expectedAsSet = Sets.fromCollection(expected);
			Set<E> actualAsSet = Sets.fromCollection(actual);
			Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must contain all " + pluralizer.nameOf(2) + " in " + expected + ".").
				addContext("Actual", actual).
				addContext("Missing", missing);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> containsAll(Collection<E> expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(expected, "expected").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (!actual.containsAll(expected))
		{
			Set<E> expectedAsSet = Sets.fromCollection(expected);
			Set<E> actualAsSet = Sets.fromCollection(actual);
			Set<E> missing = Sets.difference(expectedAsSet, actualAsSet);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must contain all " + pluralizer.nameOf(2) + " in " + name + ".").
				addContext("Actual", actual).
				addContext("Expected", expected).
				addContext("Missing", missing);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> doesNotContain(E element)
	{
		if (actual.contains(element))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not contain " + element + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> doesNotContain(E element, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual.contains(element))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " may not contain " + name + ".").
				addContext("Actual", actual).
				addContext("Unwanted", element);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> doesNotContainExactly(Collection<E> other)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<E> otherAsSet = Sets.fromCollection(other);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(otherAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, otherAsSet);

		if (missing.isEmpty() && unwanted.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not contain exactly " + other);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> doesNotContainExactly(Collection<E> other, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		Set<E> otherAsSet = Sets.fromCollection(other);
		Set<E> actualAsSet = Sets.fromCollection(actual);
		Set<E> missing = Sets.difference(otherAsSet, actualAsSet);
		Set<E> unwanted = Sets.difference(actualAsSet, otherAsSet);

		if (missing.isEmpty() && unwanted.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " may not contain exactly the same " + pluralizer.nameOf(2) + " as " + name + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> doesNotContainAny(Collection<E> elements)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();

		if (actualContainsAny(elements))
		{
			Set<E> elementsAsSet = Sets.fromCollection(elements);
			Set<E> actualAsSet = Sets.fromCollection(actual);
			Set<E> unwanted = Sets.intersection(actualAsSet, elementsAsSet);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not contain any " + pluralizer.nameOf(1) + " in " + elements + ".").
				addContext("Actual", actual).
				addContext("Unwanted", unwanted);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> doesNotContainAny(Collection<E> elements, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actualContainsAny(elements))
		{
			Set<E> elementsAsSet = Sets.fromCollection(elements);
			Set<E> actualAsSet = Sets.fromCollection(actual);
			Set<E> unwanted = Sets.intersection(actualAsSet, elementsAsSet);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " may not contain any " + pluralizer.nameOf(1) + " in " + name + ".").
				addContext("Actual", actual).
				addContext(Strings.capitalize(pluralizer.nameOf(2)), elements).
				addContext("Unwanted", unwanted);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> doesNotContainAll(Collection<E> elements)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();

		if (actual.containsAll(elements))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not contain all of " + elements + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> doesNotContainAll(Collection<E> elements, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(elements, "elements").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual.containsAll(elements))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " may not contain all " + pluralizer.nameOf(2) + " in " + name + ".").
				addContext("Actual", actual).
				addContext("Unwanted", elements);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public CollectionValidator<C, E> doesNotContainDuplicates()
	{
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
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not contain duplicate " + pluralizer.nameOf(2) + ".").
				addContext("Actual", actual).
				addContext("Duplicates", duplicates);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public SizeValidator size()
	{
		return new SizeValidatorImpl(scope, config, name, actual, name + ".size()", actual.size(), pluralizer,
			failures);
	}

	@Override
	public CollectionValidator<C, E> size(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(size());
		return getThis();
	}

	@Override
	public ArrayValidator<E> asArray(Class<E> type)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		@SuppressWarnings("unchecked")
		E[] array = (E[]) Array.newInstance(type, actual.size());
		return new ArrayValidatorImpl<>(scope, config, name, actual.toArray(array), failures);
	}

	@Override
	public CollectionValidator<C, E> asArray(Class<E> type, Consumer<ArrayValidator<E>> consumer)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asArray(type));
		return getThis();
	}
}