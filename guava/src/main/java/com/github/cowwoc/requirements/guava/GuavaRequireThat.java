package com.github.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;

/**
 * Creates validators for the Guava API, throwing an exception if a failure occurs.
 */
public interface GuavaRequireThat
{
	/**
	 * Validates the state of a {@code Multimap}.
	 *
	 * @param <K>   the type of keys in the {@code Multimap}
	 * @param <V>   the type of values in the {@code Multimap}
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> requireThat(T value, String name);
}