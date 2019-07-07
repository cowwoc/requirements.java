/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.MapValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

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
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public MapValidatorImpl(ApplicationScope scope, Configuration config, String name, Map<K, V> actual,
	                        List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected MapValidator<K, V> getThis()
	{
		return this;
	}

	@Override
	protected MapValidator<K, V> getNoOp()
	{
		return new MapValidatorNoOp<>(failures);
	}

	@Override
	public CollectionValidator<Set<K>, K> keySet()
	{
		return new CollectionValidatorImpl<>(scope, config, name + ".keySet()", actual.keySet(), Pluralizer.KEY,
			failures);
	}

	@Override
	public MapValidator<K, V> keySet(Consumer<CollectionValidator<Set<K>, K>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(keySet());
		return this;
	}

	@Override
	public CollectionValidator<Collection<V>, V> values()
	{
		return new CollectionValidatorImpl<>(scope, config, name + ".values()", actual.values(), Pluralizer.VALUE,
			failures);
	}

	@Override
	public MapValidator<K, V> values(Consumer<CollectionValidator<Collection<V>, V>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(values());
		return this;
	}

	@Override
	public CollectionValidator<Set<Entry<K, V>>, Entry<K, V>> entrySet()
	{
		return new CollectionValidatorImpl<>(scope, config, name + ".entrySet()", actual.entrySet(),
			Pluralizer.ENTRY, failures);
	}

	@Override
	public MapValidator<K, V> entrySet(Consumer<CollectionValidator<Set<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(entrySet());
		return this;
	}

	@Override
	public MapValidator<K, V> isEmpty()
	{
		if (!actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public MapValidator<K, V> isNotEmpty()
	{
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be empty");
			failures.add(failure);
		}
		return this;
	}

	@Override
	public SizeValidator size()
	{
		return new SizeValidatorImpl(scope, config, name, actual, name + ".size()", actual.size(),
			Pluralizer.ENTRY, failures);
	}

	@Override
	public MapValidator<K, V> size(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(size());
		return this;
	}
}