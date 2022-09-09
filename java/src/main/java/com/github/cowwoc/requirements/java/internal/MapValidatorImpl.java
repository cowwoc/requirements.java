/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.CollectionValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.MapValidator;
import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Default implementation of {@code MapValidator}.
 *
 * @param <K> the type of key in the map
 * @param <V> the type of value in the map
 */
public final class MapValidatorImpl<K, V>
	extends AbstractObjectValidator<MapValidator<K, V>, Map<K, V>>
	implements MapValidator<K, V>
{
	/**
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	public MapValidatorImpl(ApplicationScope scope, Configuration config, String name, Map<K, V> actual,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name, actual, failures, fatalFailure);
	}

	@Override
	protected MapValidator<K, V> getThis()
	{
		return this;
	}

	@Override
	public CollectionValidator<Set<K>, K> keySet()
	{
		if (fatalFailure)
		{
			return new CollectionValidatorImpl<>(scope, config, name + ".keySet()", null, Pluralizer.KEY,
				getFailures(), fatalFailure);
		}
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return new CollectionValidatorImpl<>(scope, config, name + ".keySet()", null, Pluralizer.KEY,
				getFailures(), fatalFailure);
		}
		return new CollectionValidatorImpl<>(scope, config, name + ".keySet()", actual.keySet(), Pluralizer.KEY,
			getFailures(), fatalFailure);
	}

	@Override
	public MapValidator<K, V> keySet(Consumer<CollectionValidator<Set<K>, K>> consumer)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(keySet());
		return this;
	}

	@Override
	public CollectionValidator<Collection<V>, V> values()
	{
		if (fatalFailure)
		{
			return new CollectionValidatorImpl<>(scope, config, name + ".values()", null, Pluralizer.VALUE,
				getFailures(), fatalFailure);
		}
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return new CollectionValidatorImpl<>(scope, config, name + ".values()", null, Pluralizer.VALUE,
				getFailures(), fatalFailure);
		}
		return new CollectionValidatorImpl<>(scope, config, name + ".values()", actual.values(), Pluralizer.VALUE,
			getFailures(), fatalFailure);
	}

	@Override
	public MapValidator<K, V> values(Consumer<CollectionValidator<Collection<V>, V>> consumer)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(values());
		return this;
	}

	@Override
	public CollectionValidator<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		if (fatalFailure)
		{
			return new CollectionValidatorImpl<>(scope, config, name + ".entrySet()", null,
				Pluralizer.ENTRY, getFailures(), fatalFailure);
		}
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return new CollectionValidatorImpl<>(scope, config, name + ".entrySet()", null,
				Pluralizer.ENTRY, getFailures(), fatalFailure);
		}
		return new CollectionValidatorImpl<>(scope, config, name + ".entrySet()", actual.entrySet(),
			Pluralizer.ENTRY, getFailures(), fatalFailure);
	}

	@Override
	public MapValidator<K, V> entrySet(Consumer<CollectionValidator<Set<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(entrySet());
		return this;
	}

	@Override
	public MapValidator<K, V> isEmpty()
	{
		if (fatalFailure)
			return getThis();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (!actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public MapValidator<K, V> isNotEmpty()
	{
		if (fatalFailure)
			return getThis();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not be empty");
			addFailure(failure);
		}
		return this;
	}

	@Override
	public SizeValidator size()
	{
		if (fatalFailure)
		{
			return new SizeValidatorImpl(scope, config, name, List.of(), name + ".size()", 0,
				Pluralizer.ENTRY, getFailures(), fatalFailure);
		}
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return new SizeValidatorImpl(scope, config, name, List.of(), name + ".size()", 0,
				Pluralizer.ENTRY, getFailures(), fatalFailure);
		}
		return new SizeValidatorImpl(scope, config, name, actual, name + ".size()", actual.size(),
			Pluralizer.ENTRY, getFailures(), fatalFailure);
	}

	@Override
	public MapValidator<K, V> size(Consumer<SizeValidator> consumer)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(size());
		return this;
	}
}