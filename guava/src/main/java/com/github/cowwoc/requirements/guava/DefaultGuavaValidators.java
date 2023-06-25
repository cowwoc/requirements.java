/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.guava;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.guava.internal.implementation.GuavaValidatorsImpl;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.ScopedContext;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import com.github.cowwoc.requirements.java.type.part.Validator;
import com.google.common.collect.Multimap;

import java.util.function.Function;

/**
 * Creates validators for the Guava API, using the default configuration.
 *
 * @see GuavaValidators#newInstance() Creating a new instance with an independent configuration
 */
public final class DefaultGuavaValidators
{
	private static final GuavaValidatorsImpl delegate = new GuavaValidatorsImpl(MainApplicationScope.INSTANCE,
		Configuration.DEFAULT);

	/**
	 * Validates the state of a {@code Multimap}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> assumeThat(T value, String name)
	{
		return delegate.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code Multimap}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> assumeThat(T value)
	{
		return delegate.assumeThat(value);
	}

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
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> checkIf(T value, String name)
	{
		return delegate.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Multimap}.
	 *
	 * @param <K>   the type of keys in the {@code Multimap}
	 * @param <V>   the type of values in the {@code Multimap}
	 * @param <T>   the type of the {@code Multimap}
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> checkIf(T value)
	{
		return delegate.checkIf(value);
	}

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
	public static <K, V, T extends Multimap<K, V>> MultimapValidator<K, V, T> requireThat(T value, String name)
	{
		return delegate.requireThat(value, name);
	}

	/**
	 * Returns the configuration used by new validators.
	 *
	 * @return the configuration used by new validators
	 */
	@CheckReturnValue
	public static Configuration configuration()
	{
		return delegate.configuration();
	}

	/**
	 * Updates the configuration used by new validators.
	 * <p>
	 * <b>NOTE</b>: Changes are only applied when {@link ConfigurationUpdater#close()} is invoked.
	 *
	 * @return the configuration updater
	 */
	@CheckReturnValue
	public static ConfigurationUpdater updateConfiguration()
	{
		return delegate.updateConfiguration();
	}

	/**
	 * Returns the contextual information for validations performed by this thread using any validator. The
	 * contextual information is a map of key-value pairs that can provide more details about validation
	 * failures. For example, if the message is "Password may not be empty" and the map contains the key-value
	 * pair {@code {"username": "john.smith"}}, the exception message would be:
	 * <p>
	 * {@snippet lang = output:
	 * Password may not be empty
	 * username: john.smith}
	 * <p>
	 * Values set by this method may be overridden by {@link Validator#context()}.
	 * <p>
	 * <b>NOTE</b>: This method affects existing and new validators used by current thread. Changes are
	 * reversed once {@link ScopedContext#close()} is invoked.
	 *
	 * @return the thread context updater
	 */
	@CheckReturnValue
	public static ScopedContext threadContext()
	{
		return delegate.threadContext();
	}

	/**
	 * Returns the global configuration shared by all validators.
	 * <p>
	 * <b>NOTE</b>: Updating This method affects existing and new validators.
	 *
	 * @return the global configuration updater
	 */
	@CheckReturnValue
	public static GlobalConfiguration globalConfiguration()
	{
		return delegate.globalConfiguration();
	}

	private DefaultGuavaValidators()
	{
	}
}