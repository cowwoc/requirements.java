/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.guava;

import com.google.common.collect.Multimap;
import io.github.cowwoc.requirements13.guava.validator.MultimapValidator;

/**
 * Creates validators for the Guava API that capture exceptions on validation failure rather than throwing
 * them immediately.
 */
public interface GuavaCheckIf
{
	/**
	 * Validates the state of a {@code Multimap}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <K>   the type of keys in the {@code Multimap}
	 * @param <V>   the type of values in the {@code Multimap}
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	<K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> checkIf(T value, String name);

	/**
	 * Validates the state of a {@code Multimap}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <K>   the type of keys in the {@code Multimap}
	 * @param <V>   the type of values in the {@code Multimap}
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @return a validator for the value
	 */
	<K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> checkIf(T value);
}