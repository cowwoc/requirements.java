/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.internal;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.guava.MultimapValidator;
import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.CollectionValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.SizeValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Default implementation of MultimapValidator.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 */
public final class MultimapValidatorImpl<K, V>
	extends AbstractObjectValidator<MultimapValidator<K, V>, Multimap<K, V>>
	implements MultimapValidator<K, V>
{
	/**
	 * @param scope    the application configuration
	 * @param name     the name of the parameter
	 * @param actual   the actual value of the parameter
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code config} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public MultimapValidatorImpl(ApplicationScope scope, String name, Multimap<K, V> actual,
	                             Configuration config, List<ValidationFailure> failures)
	{
		super(scope, name, actual, config, failures);
	}

	@Override
	public CollectionValidator<Set<K>, K> keySet()
	{
		return new CollectionValidatorImpl<>(scope, name + ".keySet()", actual.keySet(), Pluralizer.KEY,
			config, failures);
	}

	@Override
	public MultimapValidator<K, V> keySet(Consumer<CollectionValidator<Set<K>, K>> consumer)
	{
		consumer.accept(keySet());
		return this;
	}

	@Override
	public CollectionValidator<Collection<V>, V> values()
	{
		return new CollectionValidatorImpl<>(scope, name + ".values()", actual.values(), Pluralizer.VALUE,
			config, failures);
	}

	@Override
	public MultimapValidator<K, V> values(Consumer<CollectionValidator<Collection<V>, V>> consumer)
	{
		consumer.accept(values());
		return this;
	}

	@Override
	public CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>> entries()
	{
		return new CollectionValidatorImpl<>(scope, name + ".entries()", actual.entries(), Pluralizer.ENTRY,
			config, failures);
	}

	@Override
	public MultimapValidator<K, V> entries(
		Consumer<CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>>> consumer)
	{
		consumer.accept(entries());
		return this;
	}

	@Override
	public MultimapValidator<K, V> isEmpty()
	{
		if (actual.isEmpty())
			return this;
		String actualAsString = config.toString(actual);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be empty.").
			addContext("Actual", actualAsString).
			build();
	}

	@Override
	public MultimapValidator<K, V> isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be empty").
			build();
	}

	@Override
	public SizeValidator size()
	{
		return new SizeValidatorImpl(scope, name, actual, name + ".size()", actual.size(),
			Pluralizer.ENTRY, config, failures);
	}

	@Override
	public MultimapValidator<K, V> size(Consumer<SizeValidator> consumer)
	{
		consumer.accept(size());
		return this;
	}
}
