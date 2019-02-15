/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.java.Configuration;

import java.util.function.Function;

/**
 * Verifies the requirements of the Guava library API.
 * <p>
 * Implementations must be immutable.
 */
public interface GuavaVerifier extends Configuration
{
	@Override
	GuavaVerifier putContext(String name, Object value);

	@Override
	GuavaVerifier removeContext(String name);

	@Override
	GuavaVerifier withDefaultException();

	@Override
	GuavaVerifier withException(Class<? extends RuntimeException> exception);

	@Override
	GuavaVerifier withAssertionsDisabled();

	@Override
	GuavaVerifier withAssertionsEnabled();

	@Override
	GuavaVerifier withDiff();

	@Override
	GuavaVerifier withoutDiff();

	@Override
	<T> GuavaVerifier withStringConverter(Class<T> type, Function<T, String> converter);

	@Override
	<T> GuavaVerifier withoutStringConverter(Class<T> type);

	@Override
	GuavaVerifier withConfiguration(Configuration configuration);

	/**
	 * Verifies the requirements of a {@code Multimap}.
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
