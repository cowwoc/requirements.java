/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.core.Configurable;
import org.bitbucket.cowwoc.requirements.core.Configuration;

/**
 * An entry point for verifying API requirements.
 * <p>
 * Unlike {@link Requirements}, instances of this interface are configurable.
 * <p>
 * Implementations must be immutable.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
public interface GuavaVerifiers extends Configurable
{
	@Override
	GuavaVerifiers addContext(String name, Object value);

	@Override
	GuavaVerifiers withDefaultException();

	@Override
	GuavaVerifiers withException(Class<? extends RuntimeException> exception);

	@Override
	GuavaVerifiers withAssertionsDisabled();

	@Override
	GuavaVerifiers withAssertionsEnabled();

	@Override
	boolean assertionsAreEnabled();

	@Override
	GuavaVerifiers withConfiguration(Configuration configuration);

	/**
	 * Verifies a {@code Multimap}.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name);

	/**
	 * Same as {@link #requireThat(Multimap, String)} but does nothing if assertions are disabled.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name);
}
