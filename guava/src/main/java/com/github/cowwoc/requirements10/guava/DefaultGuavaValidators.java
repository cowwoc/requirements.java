/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.guava;

import com.github.cowwoc.requirements10.annotation.CheckReturnValue;
import com.github.cowwoc.requirements10.guava.internal.validator.GuavaValidatorsImpl;
import com.github.cowwoc.requirements10.guava.validator.MultimapValidator;
import com.github.cowwoc.requirements10.java.GlobalConfiguration;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.scope.MainApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.StampedLocks;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;
import com.google.common.collect.Multimap;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.StampedLock;

/**
 * Creates validators for the Guava API.
 * <p>
 * There are three kinds of validators:
 * <ul>
 *   <li>{@code requireThat()} for method preconditions.</li>
 *   <li>{@code assert that()} for class invariants, and method postconditions.</li>
 *   <li>{@code checkIf()} for returning multiple validation failures.</li>
 * </ul>
 * <p>
 * <b>Thread Safety</b>: This class is thread-safe.
 */
public final class DefaultGuavaValidators
{
	private static final GuavaValidatorsImpl DELEGATE = new GuavaValidatorsImpl(MainApplicationScope.INSTANCE,
		Configuration.DEFAULT);
	private static final StampedLock CONTEXT_LOCK = new StampedLock();

	/**
	 * Validates the state of a {@code Multimap}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
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
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> requireThat(T value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Multimap}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> that(T value, String name)
	{
		return DELEGATE.assertThat(value, name);
	}

	/**
	 * Validates the state of a {@code Multimap}. Validation failures only throw {@code AssertionError}.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> that(T value)
	{
		return DELEGATE.assertThat(value);
	}

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
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> checkIf(T value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

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
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<T, K, V> checkIf(T value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Returns the contextual information for validators created out by this factory. The contextual information
	 * is a map of key-value pairs that can provide more details about validation failures. For example, if the
	 * message is "Password may not be empty" and the map contains the key-value pair
	 * {@code {"username": "john.smith"}}, the exception message would be:
	 * <p>
	 * {@snippet lang = output:
	 * Password may not be empty
	 * username: john.smith}
	 * <p>
	 * Note that values are wrapped in an {@code Optional} because modern maps do not support {@code null}
	 * values.
	 *
	 * @return an unmodifiable map from each entry's name to its value
	 */
	public static Map<String, Optional<Object>> getContext()
	{
		return StampedLocks.optimisticRead(CONTEXT_LOCK, DELEGATE::getContext);
	}

	/**
	 * Sets the contextual information for validators created by this factory.
	 * <p>
	 * This method adds contextual information to exception messages. The contextual information is stored as
	 * key-value pairs in a map. Values set by this method may be overridden by
	 * {@link ValidatorComponent#withContext(Object, String)}}.
	 *
	 * @param value the value of the entry
	 * @param name  the name of an entry
	 * @return the underlying validator factory
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                    <li>is already in use by the value being validated or the validator
	 *                                    context</li>
	 *                                  </ul>
	 */
	public static GuavaValidators withContext(Object value, String name)
	{
		return StampedLocks.write(CONTEXT_LOCK, () -> DELEGATE.withContext(value, name));
	}

	/**
	 * Removes the contextual information of validators created by this factory.
	 *
	 * @param name the parameter name
	 * @return the underlying validator factory
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                  </ul>
	 */
	public static GuavaValidators removeContext(String name)
	{
		return StampedLocks.write(CONTEXT_LOCK, () -> DELEGATE.removeContext(name));
	}

	/**
	 * Returns the global configuration shared by all validators.
	 * <p>
	 * <b>NOTE</b>: Updating this configuration affects existing and new validators.
	 *
	 * @return the global configuration updater
	 */
	@CheckReturnValue
	public static GlobalConfiguration globalConfiguration()
	{
		return DELEGATE.globalConfiguration();
	}

	private DefaultGuavaValidators()
	{
	}
}