/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava.spi;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.spi.NoOpCollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.spi.NoOpContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.guava.MultimapVerifier;

/**
 * @author Gili Tzabari
 */
public final class NoOpMultimapVerifier implements MultimapVerifier<Object, Object>
{
	// Enum declares values() which conflicts with Multimap.values()
	public static final NoOpMultimapVerifier INSTANCE = new NoOpMultimapVerifier();

	/**
	 * Prevent construction.
	 */
	private NoOpMultimapVerifier()
	{
	}

	@Override
	public CollectionVerifier<Object> keySet()
	{
		return new NoOpCollectionVerifier<>();
	}

	@Override
	public CollectionVerifier<Object> values()
	{
		return new NoOpCollectionVerifier<>();
	}

	@Override
	public CollectionVerifier<Map.Entry<Object, Object>> entrySet()
	{
		return new NoOpCollectionVerifier<>();
	}

	@Override
	public MultimapVerifier<Object, Object> isEmpty()
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isNotEmpty()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier size()
	{
		return NoOpContainerSizeVerifier.INSTANCE;
	}

	@Override
	public MultimapVerifier<Object, Object> withException(
		Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> withContext(List<Map.Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isEqualTo(Multimap<Object, Object> value)
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isEqualTo(Multimap<Object, Object> value, String name)
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isNotEqualTo(Multimap<Object, Object> value)
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isNotEqualTo(Multimap<Object, Object> value, String name)
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isIn(Collection<Multimap<Object, Object>> collection)
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isInstanceOf(
		Class<?> type)
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isNull()
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isNotNull()
	{
		return this;
	}

	@Override
	public MultimapVerifier<Object, Object> isolate(
		Consumer<MultimapVerifier<Object, Object>> consumer)
	{
		return this;
	}
}
