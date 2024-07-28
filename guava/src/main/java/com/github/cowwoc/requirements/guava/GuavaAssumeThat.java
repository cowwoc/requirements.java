package com.github.cowwoc.requirements.guava;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.google.common.collect.Multimap;

import java.util.function.Function;

/**
 * Creates validators for the Guava API, throwing an exception if a failure occurs.
 */
public interface GuavaAssumeThat
{
	/**
	 * Validates the state of a {@code Multimap}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
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
	<K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assumeThat(T value, String name);

	/**
	 * Validates the state of a {@code Multimap}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <K>   the type of keys in the {@code Multimap}
	 * @param <V>   the type of values in the {@code Multimap}
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @return a validator for the value
	 */
	<K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> assumeThat(T value);
}