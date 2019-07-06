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
import org.bitbucket.cowwoc.requirements.java.StringValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.CollectionValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.SizeValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * An {@code MultimapValidator} that does nothing.
 *
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 */
public final class MultimapValidatorNoOp<K, V>
	extends AbstractObjectValidatorNoOp<MultimapValidator<K, V>, Multimap<K, V>>
	implements MultimapValidator<K, V>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null. If {@code name}
	 *                        is empty.
	 */
	public MultimapValidatorNoOp(ApplicationScope scope, Configuration config,
	                             List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected MultimapValidator<K, V> getThis()
	{
		return this;
	}

	@Override
	public CollectionValidator<Set<K>, K> keySet()
	{
		return new CollectionValidatorNoOp<>(scope, config, failures);
	}

	@Override
	public MultimapValidator<K, V> keySet(Consumer<CollectionValidator<Set<K>, K>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionValidator<Collection<V>, V> values()
	{
		return new CollectionValidatorNoOp<>(scope, config, failures);
	}

	@Override
	public MultimapValidator<K, V> values(Consumer<CollectionValidator<Collection<V>, V>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>> entries()
	{
		return new CollectionValidatorNoOp<>(scope, config, failures);
	}

	@Override
	public MultimapValidator<K, V> entries(Consumer<CollectionValidator<Collection<Entry<K, V>>, Entry<K, V>>>
		                                       consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public MultimapValidator<K, V> isEmpty()
	{
		return this;
	}

	@Override
	public MultimapValidator<K, V> isNotEmpty()
	{
		return this;
	}

	@Override
	public SizeValidator size()
	{
		return new SizeValidatorNoOp(scope, config, failures);
	}

	@Override
	public MultimapValidator<K, V> size(Consumer<SizeValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public List<ValidationFailure> getFailures()
	{
		return failures;
	}

	@Override
	public MultimapValidator<K, V> asString(Consumer<StringValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}