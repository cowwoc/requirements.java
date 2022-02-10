/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ArrayValidator;
import com.github.cowwoc.requirements.java.CollectionValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.ListValidator;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Default implementation of {@code ArrayValidator}.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public final class ArrayValidatorImpl<A, E> extends AbstractObjectValidator<ArrayValidator<A, E>, A>
	implements ArrayValidator<A, E>
{
	private final List<E> actualAsList;
	private final ListValidator<List<E>, E> asList;

	/**
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param actualAsList the {@code List} representation of the array
	 * @param failures     the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public ArrayValidatorImpl(ApplicationScope scope, Configuration config, String name, A actual,
	                          List<E> actualAsList, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
		this.actualAsList = actualAsList;
		this.asList = new ListValidatorImpl<>(scope, config, name, actualAsList, Pluralizer.ELEMENT, failures);
	}

	@Override
	protected ArrayValidator<A, E> getNoOp()
	{
		return new ArrayValidatorNoOp<>(getFailures());
	}

	@Override
	protected ArrayValidator<A, E> getThis()
	{
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isEqualTo(Object expected)
	{
		asList.isEqualTo(expected);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isEqualTo(Object expected, String name)
	{
		asList.isEqualTo(expected, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isNotEqualTo(Object value)
	{
		asList.isNotEqualTo(value);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isNotEqualTo(Object value, String name)
	{
		asList.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isInstanceOf(Class<?> type)
	{
		asList.isInstanceOf(type);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isNotInstanceOf(Class<?> type)
	{
		asList.isNotInstanceOf(type);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isNull()
	{
		asList.isNull();
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isNotNull()
	{
		asList.isNotNull();
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isEmpty()
	{
		asList.isEmpty();
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isNotEmpty()
	{
		asList.isNotEmpty();
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> contains(E expected)
	{
		asList.contains(expected);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> contains(E expected, String name)
	{
		asList.contains(expected, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsExactly(Collection<E> expected)
	{
		asList.containsExactly(expected);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsExactly(Collection<E> expected, String name)
	{
		asList.containsExactly(expected, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsAny(Collection<E> expected)
	{
		asList.containsAny(expected);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsAny(Collection<E> elements, String name)
	{
		asList.containsAny(elements, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsAll(Collection<E> expected)
	{
		asList.containsAll(expected);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsAll(Collection<E> expected, String name)
	{
		asList.containsAll(expected, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContain(E element)
	{
		asList.doesNotContain(element);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContain(E element, String name)
	{
		asList.doesNotContain(element, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainExactly(Collection<E> other)
	{
		asList.doesNotContainExactly(other);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainExactly(Collection<E> other, String name)
	{
		asList.doesNotContainExactly(other, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainAny(Collection<E> elements)
	{
		asList.doesNotContainAny(elements);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainAny(Collection<E> elements, String name)
	{
		asList.doesNotContainAny(elements, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainAll(Collection<E> elements)
	{
		asList.doesNotContainAll(elements);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainAll(Collection<E> elements, String name)
	{
		asList.doesNotContainAll(elements, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainDuplicates()
	{
		asList.doesNotContainDuplicates();
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isSorted(Comparator<E> comparator)
	{
		asList.isSorted(comparator);
		return this;
	}

	@Override
	public SizeValidator length()
	{
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return new SizeValidatorNoOp(getFailures());
		}
		return new SizeValidatorImpl(scope, config, name, actual, name + ".length", actualAsList.size(),
			Pluralizer.ELEMENT, getFailures());
	}

	@Override
	public ArrayValidatorImpl<A, E> length(Consumer<SizeValidator> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(length());
		return this;
	}

	@Override
	public CollectionValidator<Collection<E>, E> asCollection()
	{
		return new CollectionValidatorImpl<>(scope, config, name, actualAsList, Pluralizer.ELEMENT, getFailures());
	}

	@Override
	public ArrayValidatorImpl<A, E> asCollection(Consumer<CollectionValidator<Collection<E>, E>> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asCollection());
		return this;
	}

	@Override
	public ListValidator<List<E>, E> asList()
	{
		return asList;
	}

	@Override
	public ArrayValidatorImpl<A, E> asList(Consumer<ListValidator<List<E>, E>> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asList());
		return this;
	}

	@Override
	public List<ValidationFailure> getFailures()
	{
		List<ValidationFailure> collectionFailures = asList.getFailures();
		List<ValidationFailure> arrayFailures = super.getFailures();
		if (collectionFailures.isEmpty() && arrayFailures.isEmpty())
			return collectionFailures;
		List<ValidationFailure> result = new ArrayList<>(collectionFailures.size() + arrayFailures.size());
		result.addAll(collectionFailures);
		result.addAll(arrayFailures);
		return result;
	}
}