/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.java.Configuration;

import java.util.function.Function;

/**
 * Verifies the requirements types from of the Guava library API.
 * <p>
 * Implementations must be thread-safe.
 */
public interface GuavaRequirements extends Configuration
{
	@Override
	GuavaRequirements putContext(String name, Object value);

	@Override
	GuavaRequirements removeContext(String name);

	@Override
	GuavaRequirements withAssertionsDisabled();

	@Override
	GuavaRequirements withAssertionsEnabled();

	@Override
	GuavaRequirements withCleanStackTrace();

	@Override
	GuavaRequirements withoutCleanStackTrace();

	@Override
	GuavaRequirements withDiff();

	@Override
	GuavaRequirements withoutDiff();

	@Override
	<T> GuavaRequirements withStringConverter(Class<T> type, Function<T, String> converter);

	@Override
	<T> GuavaRequirements withoutStringConverter(Class<T> type);

	@Override
	GuavaRequirements withConfiguration(Configuration configuration);

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

	/**
	 * Verifies the requirements of a {@code Multimap}.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<K, V> MultimapValidator<K, V> validateThat(Multimap<K, V> actual, String name);
}
