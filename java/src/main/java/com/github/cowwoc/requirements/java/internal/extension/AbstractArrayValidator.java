/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.internal.CollectionValidatorImpl;
import com.github.cowwoc.requirements.java.internal.SizeValidatorNoOp;
import com.github.cowwoc.requirements.java.internal.ValidationFailureImpl;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.CollectionValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.extension.ExtensibleArrayValidator;
import com.github.cowwoc.requirements.java.internal.SizeValidatorImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Extensible implementation of ExtensibleArrayValidator.
 *
 * @param <S> the type of validator returned by the methods
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public abstract class AbstractArrayValidator<S, A, E> extends AbstractObjectValidator<S, A>
	implements ExtensibleArrayValidator<S, A, E>
{
	private final Collection<E> actualAsCollection;
	private final CollectionValidator<Collection<E>, E> asCollection;

	/**
	 * @param scope              the application configuration
	 * @param config             the instance configuration
	 * @param name               the name of the value
	 * @param actual             the actual value
	 * @param actualAsCollection the {@code Collection} representation of the array
	 * @param failures           the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	protected AbstractArrayValidator(ApplicationScope scope, Configuration config, String name, A actual,
	                                 Collection<E> actualAsCollection, List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
		this.actualAsCollection = actualAsCollection;
		this.asCollection = new CollectionValidatorImpl<>(scope, config, name, actualAsCollection,
			Pluralizer.ELEMENT, failures);
	}

	@Override
	public S isEqualTo(Object expected)
	{
		asCollection.isEqualTo(expected);
		return getThis();
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		asCollection.isEqualTo(expected, name);
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object value)
	{
		asCollection.isNotEqualTo(value);
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object value, String name)
	{
		asCollection.isNotEqualTo(value, name);
		return getThis();
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		asCollection.isInstanceOf(type);
		return getThis();
	}

	@Override
	public S isNotInstanceOf(Class<?> type)
	{
		asCollection.isNotInstanceOf(type);
		return getThis();
	}

	@Override
	public S isNull()
	{
		asCollection.isNull();
		return getThis();
	}

	@Override
	public S isNotNull()
	{
		asCollection.isNotNull();
		return getThis();
	}

	@Override
	public S isEmpty()
	{
		asCollection.isEmpty();
		return getThis();
	}

	@Override
	public S isNotEmpty()
	{
		asCollection.isNotEmpty();
		return getThis();
	}

	@Override
	public S contains(E expected)
	{
		asCollection.contains(expected);
		return getThis();
	}

	@Override
	public S contains(E expected, String name)
	{
		asCollection.contains(expected, name);
		return getThis();
	}

	@Override
	public S containsExactly(Collection<E> expected)
	{
		asCollection.containsExactly(expected);
		return getThis();
	}

	@Override
	public S containsExactly(Collection<E> expected, String name)
	{
		asCollection.containsExactly(expected, name);
		return getThis();
	}

	@Override
	public S containsAny(Collection<E> expected)
	{
		asCollection.containsAny(expected);
		return getThis();
	}

	@Override
	public S containsAny(Collection<E> elements, String name)
	{
		asCollection.containsAny(elements, name);
		return getThis();
	}

	@Override
	public S containsAll(Collection<E> expected)
	{
		asCollection.containsAll(expected);
		return getThis();
	}

	@Override
	public S containsAll(Collection<E> expected, String name)
	{
		asCollection.containsAll(expected, name);
		return getThis();
	}

	@Override
	public S doesNotContain(E element)
	{
		asCollection.doesNotContain(element);
		return getThis();
	}

	@Override
	public S doesNotContain(E element, String name)
	{
		asCollection.doesNotContain(element, name);
		return getThis();
	}

	@Override
	public S doesNotContainExactly(Collection<E> other)
	{
		asCollection.doesNotContainExactly(other);
		return getThis();
	}

	@Override
	public S doesNotContainExactly(Collection<E> other, String name)
	{
		asCollection.doesNotContainExactly(other, name);
		return getThis();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements)
	{
		asCollection.doesNotContainAny(elements);
		return getThis();
	}

	@Override
	public S doesNotContainAny(Collection<E> elements, String name)
	{
		asCollection.doesNotContainAny(elements, name);
		return getThis();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements)
	{
		asCollection.doesNotContainAll(elements);
		return getThis();
	}

	@Override
	public S doesNotContainAll(Collection<E> elements, String name)
	{
		asCollection.doesNotContainAll(elements, name);
		return getThis();
	}

	@Override
	public S doesNotContainDuplicates()
	{
		asCollection.doesNotContainDuplicates();
		return getThis();
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
		return new SizeValidatorImpl(scope, config, name, actual, name + ".length", actualAsCollection.size(),
			Pluralizer.ELEMENT, getFailures());
	}

	@Override
	public S length(Consumer<SizeValidator> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(length());
		return getThis();
	}

	@Override
	public CollectionValidator<Collection<E>, E> asCollection()
	{
		return asCollection;
	}

	@Override
	public S asCollection(Consumer<CollectionValidator<Collection<E>, E>> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asCollection());
		return getThis();
	}

	@Override
	public List<ValidationFailure> getFailures()
	{
		List<ValidationFailure> collectionFailures = asCollection.getFailures();
		List<ValidationFailure> arrayFailures = super.getFailures();
		if (collectionFailures.isEmpty() && arrayFailures.isEmpty())
			return collectionFailures;
		List<ValidationFailure> result = new ArrayList<>(collectionFailures.size() + arrayFailures.size());
		result.addAll(collectionFailures);
		result.addAll(arrayFailures);
		return result;
	}
}
