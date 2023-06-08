/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.pouch.core.LazyReference;
import com.github.cowwoc.pouch.core.Reference;
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

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Default implementation of {@code ArrayValidator}.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public final class ArrayValidatorImpl<A, E> extends AbstractObjectValidator<ArrayValidator<A, E>, A>
	implements ArrayValidator<A, E>
{
	private final Reference<List<E>> actualAsList;
	private final Reference<ListValidator<List<E>, E>> asList;
	private final BiFunction<Object, Object, Boolean> equals;
	private final Function<Object, Boolean> contains;
	private final Supplier<Integer> length;

	/**
	 * @param scope                the application configuration
	 * @param config               the instance configuration
	 * @param name                 the name of the value
	 * @param actual               the actual value
	 * @param actualAsListSupplier returns a {@code List} representation of the array
	 * @param equals               returns true if the array is equal to an object
	 * @param contains             returns true if the array contains a value
	 * @param length               returns the length of the array
	 * @param failures             the list of validation failures
	 * @param fatalFailure         true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if any of the parameters except for {@code actual} are null. If {@code name} is
	 *                        empty.
	 */
	public ArrayValidatorImpl(ApplicationScope scope, Configuration config, String name, A actual,
		Supplier<List<E>> actualAsListSupplier, BiFunction<Object, Object, Boolean> equals,
		Function<Object, Boolean> contains, Supplier<Integer> length, List<ValidationFailure> failures,
		boolean fatalFailure)
	{
		// DESIGN NOTES:
		// * "A" is not always equal to E[]. Example: for primitive arrays we construct
		//   ArrayValidator<byte[], Byte>. The second type parameter is a wrapper type because Java
		//   type-parameters cannot be primitives.
		// * We use lazy-loading to defer converting the array into a list until absolutely
		// necessary. This conversion is expensive for large arrays.
		super(scope, config, name, actual, failures, fatalFailure);
		assert actualAsListSupplier != null;
		assert equals != null;
		assert contains != null;
		this.actualAsList = LazyReference.create(actualAsListSupplier);
		this.asList = LazyReference.create(() ->
			new ListValidatorImpl<>(scope, config, name, actualAsListSupplier.get(), Pluralizer.ELEMENT, failures,
				fatalFailure));
		this.equals = equals;
		this.contains = contains;
		this.length = length;
	}

	@Override
	protected ArrayValidator<A, E> getThis()
	{
		return this;
	}

	@Override
	public ArrayValidator<A, E> isEqualTo(Object expected)
	{
		return isEqualTo(expected, equals);
	}

	@Override
	public ArrayValidator<A, E> isEqualTo(Object expected, String name)
	{
		return isEqualTo(expected, name, equals);
	}

	@Override
	public ArrayValidator<A, E> isNotEqualTo(Object value)
	{
		return isNotEqualTo(value, equals);
	}

	@Override
	public ArrayValidator<A, E> isNotEqualTo(Object value, String name)
	{
		return isNotEqualTo(value, name, equals);
	}

	@Override
	public ArrayValidator<A, E> isEmpty()
	{
		return isEmpty(length, "Actual.length");
	}

	@Override
	public ArrayValidator<A, E> isNotEmpty()
	{
		return isNotEmpty(length);
	}

	@Override
	public ArrayValidator<A, E> contains(E expected)
	{
		return contains(expected, contains);
	}

	@Override
	public ArrayValidator<A, E> contains(E expected, String name)
	{
		return super.contains(expected, name, contains);
	}

	@Override
	public ArrayValidatorImpl<A, E> containsExactly(Collection<E> expected)
	{
		if (fatalFailure)
			return this;
		asList.getValue().containsExactly(expected);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsExactly(Collection<E> expected, String name)
	{
		if (fatalFailure)
			return this;
		asList.getValue().containsExactly(expected, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsAny(Collection<E> expected)
	{
		if (fatalFailure)
			return this;
		asList.getValue().containsAny(expected);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsAny(Collection<E> elements, String name)
	{
		if (fatalFailure)
			return this;
		asList.getValue().containsAny(elements, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsAll(Collection<E> expected)
	{
		if (fatalFailure)
			return this;
		asList.getValue().containsAll(expected);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> containsAll(Collection<E> expected, String name)
	{
		if (fatalFailure)
			return this;
		asList.getValue().containsAll(expected, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContain(E element)
	{
		super.doesNotContain(element, contains);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContain(E element, String name)
	{
		super.doesNotContain(element, name, contains);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainExactly(Collection<E> other)
	{
		if (fatalFailure)
			return this;
		asList.getValue().doesNotContainExactly(other);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainExactly(Collection<E> other, String name)
	{
		if (fatalFailure)
			return this;
		asList.getValue().doesNotContainExactly(other, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainAny(Collection<E> elements)
	{
		if (fatalFailure)
			return this;
		asList.getValue().doesNotContainAny(elements);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainAny(Collection<E> elements, String name)
	{
		if (fatalFailure)
			return this;
		asList.getValue().doesNotContainAny(elements, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainAll(Collection<E> elements)
	{
		if (fatalFailure)
			return this;
		asList.getValue().doesNotContainAll(elements);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainAll(Collection<E> elements, String name)
	{
		if (fatalFailure)
			return this;
		asList.getValue().doesNotContainAll(elements, name);
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> doesNotContainDuplicates()
	{
		if (fatalFailure)
			return this;
		asList.getValue().doesNotContainDuplicates();
		return this;
	}

	@Override
	public ArrayValidatorImpl<A, E> isSorted(Comparator<E> comparator)
	{
		if (fatalFailure)
			return this;
		asList.getValue().isSorted(comparator);
		return this;
	}

	@Override
	public SizeValidator length()
	{
		if (fatalFailure)
		{
			return new SizeValidatorImpl(scope, config, name, List.of(), name + ".length", 0,
				Pluralizer.ELEMENT, getFailures(), fatalFailure);
		}
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return new SizeValidatorImpl(scope, config, name, List.of(), name + ".length", 0,
				Pluralizer.ELEMENT, getFailures(), fatalFailure);
		}
		return new SizeValidatorImpl(scope, config, name, actual, name + ".length", length.get(),
			Pluralizer.ELEMENT, getFailures(), fatalFailure);
	}

	@Override
	public ArrayValidatorImpl<A, E> length(Consumer<SizeValidator> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(length());
		return this;
	}

	@Override
	public CollectionValidator<Collection<E>, E> asCollection()
	{
		if (fatalFailure)
		{
			return new CollectionValidatorImpl<>(scope, config, name, null, Pluralizer.ELEMENT, getFailures(),
				fatalFailure);
		}
		return new CollectionValidatorImpl<>(scope, config, name, actualAsList.getValue(), Pluralizer.ELEMENT,
			getFailures(), fatalFailure);
	}

	@Override
	public ArrayValidatorImpl<A, E> asCollection(Consumer<CollectionValidator<Collection<E>, E>> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asCollection());
		return this;
	}

	@Override
	public ListValidator<List<E>, E> asList()
	{
		if (fatalFailure)
			return new ListValidatorImpl<>(scope, config, name, null, Pluralizer.ELEMENT, failures, true);
		return asList.getValue();
	}

	@Override
	public ArrayValidatorImpl<A, E> asList(Consumer<ListValidator<List<E>, E>> consumer)
	{
		if (fatalFailure)
			return this;
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asList());
		return this;
	}
}