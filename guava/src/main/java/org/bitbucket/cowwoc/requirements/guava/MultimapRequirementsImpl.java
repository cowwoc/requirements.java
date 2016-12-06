/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.CollectionRequirements;
import org.bitbucket.cowwoc.requirements.ObjectRequirements;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of MultimapRequirements.
 * <p>
 * @param <K> the type of key in the multimap
 * @param <V> the type of value in the multimap
 * @author Gili Tzabari
 */
final class MultimapRequirementsImpl<K, V> implements MultimapRequirements<K, V>
{
	private final SingletonScope scope;
	private final Multimap<K, V> parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Multimap<K, V>> asObject;

	/**
	 * Creates new MultimapRequirementsImpl.
	 * <p>
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	MultimapRequirementsImpl(SingletonScope scope, Multimap<K, V> actual, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.parameter = actual;
		this.name = name;
		this.config = config;
		this.asObject = scope.getInternalVerifier().requireThat(actual, name).
			withContext(config.getContext()).
			withException(config.getException());
	}

	@Override
	public MultimapRequirements<K, V> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MultimapRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public MultimapRequirements<K, V> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new MultimapRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public MultimapRequirements<K, V> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new MultimapRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public MultimapRequirements<K, V> isEqualTo(Multimap<K, V> value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public MultimapRequirements<K, V> isEqualTo(Multimap<K, V> value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public MultimapRequirements<K, V> isNotEqualTo(Multimap<K, V> value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public MultimapRequirements<K, V> isNotEqualTo(Multimap<K, V> value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public MultimapRequirements<K, V> isIn(Collection<Multimap<K, V>> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public MultimapRequirements<K, V> isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public MultimapRequirements<K, V> isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public MultimapRequirements<K, V> isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public CollectionRequirements<K> keySet()
	{
		return new CollectionRequirementsImpl<>(scope, parameter.keySet(), name + ".keySet()", config,
			Pluralizer.KEY).
			addContext(name, parameter);
	}

	@Override
	public CollectionRequirements<V> values()
	{
		return new MultimapValuesRequirementsImpl<>(scope, parameter, name, config);
	}

	@Override
	public CollectionRequirements<Entry<K, V>> entrySet()
	{
		return new MultimapEntrySetRequirementsImpl<>(scope, parameter, name, config);
	}

	@Override
	public MultimapRequirements<K, V> isEmpty()
	{
		if (parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public MultimapRequirements<K, V> isNotEmpty()
	{
		if (!parameter.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be empty", name)).
			build();
	}

	@Override
	public MultimapSizeRequirements size()
	{
		return new MultimapSizeRequirementsImpl(scope, parameter, name, config);
	}

	@Override
	public MultimapRequirements<K, V> isolate(Consumer<MultimapRequirements<K, V>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
