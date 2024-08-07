package com.github.cowwoc.requirements10.guava.internal;

import com.github.cowwoc.requirements10.guava.validator.MultimapValidator;
import com.google.common.collect.Multimap;

/**
 * Creates validators for the Guava API that throw {@code AssertionError} immediately on validation failure.
 */
public interface GuavaAssertThat
{
	/**
	 * Validates the state of a {@code Multimap}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
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
	<K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assertThat(T value, String name);

	/**
	 * Validates the state of a {@code Multimap}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <K>   the type of keys in the {@code Multimap}
	 * @param <V>   the type of values in the {@code Multimap}
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @return a validator for the value
	 */
	<K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assertThat(T value);
}