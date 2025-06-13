package com.github.cowwoc.requirements11.guava;

import com.github.cowwoc.requirements11.guava.validator.MultimapValidator;
import com.google.common.collect.Multimap;

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