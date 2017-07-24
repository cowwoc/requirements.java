/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;

/**
 * An interface that forwards method invocations to a {@link GuavaVerifiers} instance.
 *
 * @author Gili Tzabari
 */
public interface ForwardingGuavaVerifiers extends GuavaVerifiers
{
	/**
	 * @return the {@code GuavaVerifiers} instance to forward calls to
	 */
	GuavaVerifiers guavaVerifiers();

	@Override
	default boolean assertionsAreEnabled()
	{
		return guavaVerifiers().assertionsAreEnabled();
	}

	@Override
	default GuavaVerifiers withAssertionsEnabled()
	{
		return guavaVerifiers().withAssertionsEnabled();
	}

	@Override
	default GuavaVerifiers withAssertionsDisabled()
	{
		return guavaVerifiers().withAssertionsDisabled();
	}

	@Override
	default <K, V> MultimapVerifier<K, V> requireThat(String name, Multimap<K, V> actual)
	{
		return guavaVerifiers().requireThat(name, actual);
	}

	@Override
	default <K, V> MultimapVerifier<K, V> assertThat(String name, Multimap<K, V> actual)
	{
		return guavaVerifiers().assertThat(name, actual);
	}
}
