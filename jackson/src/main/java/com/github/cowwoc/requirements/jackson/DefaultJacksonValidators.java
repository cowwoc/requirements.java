/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.jackson.internal.validator.JacksonValidatorsImpl;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.CloseableLock;
import com.github.cowwoc.requirements.java.internal.util.ReentrantStampedLock;
import com.github.cowwoc.requirements.java.validator.component.ValidatorComponent;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Creates validators for the Jackson API, using the default configuration.
 * <p>
 * There are three kinds of validators:
 * <ul>
 *   <li>{@code requireThat()} for method preconditions.</li>
 *   <li>{@code assumeThat()} for class invariants, and method postconditions.</li>
 *   <li>{@code checkIf()} for method preconditions, class invariants and method postconditions.</li>
 * </ul>
 * <p>
 * {@code requireThat()} and {@code assumeThat()} throw an exception on the first validation failure,
 * while {@code checkIf()} collects multiple validation failures before throwing an exception at the end.
 * {@code checkIf()} is more flexible than the others, but its syntax is more verbose.
 * <p>
 * Exceptions that are thrown in response to invalid method arguments (e.g.
 * {@code isGreaterThan(value, null)} are thrown by all validators and cannot be configured. Exceptions that
 * are thrown in response to the value failing a validation check, e.g. {@code isGreaterThan(5)} on a value
 * of 0, are thrown by {@code requireThat()} and {@code assumeThat()} but are recorded by {@code checkIf()}
 * without being thrown. The type of thrown exceptions is configurable using
 * {@link ConfigurationUpdater#exceptionTransformer(Function)}.
 * <p>
 * <b>Thread Safety</b>: This class is thread-safe.
 *
 * @see JacksonValidators#newInstance() Creating a new instance with an independent configuration
 */
public final class DefaultJacksonValidators
{
	private static final JacksonValidatorsImpl DELEGATE = new JacksonValidatorsImpl(
		MainApplicationScope.INSTANCE,
		Configuration.DEFAULT);
	private static final ReentrantStampedLock CONTEXT_LOCK = new ReentrantStampedLock();

	/**
	 * Validates the state of a {@code JsonNode}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if any of the mandatory arguments are null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	public static <T extends JsonNode> JsonNodeValidator<T> assumeThat(T value, String name)
	{
		return DELEGATE.assumeThat(value, name);
	}

	/**
	 * Validates the state of a {@code JsonNode}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T extends JsonNode> JsonNodeValidator<T> assumeThat(T value)
	{
		return DELEGATE.assumeThat(value);
	}

	/**
	 * Validates the state of a {@code JsonNode}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if any of the mandatory arguments are null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	public static <T extends JsonNode> JsonNodeValidator<T> checkIf(T value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code JsonNode}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T extends JsonNode> JsonNodeValidator<T> checkIf(T value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code JsonNode}.
	 *
	 * @param <T>   the type of the {@code JsonNode}
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if any of the mandatory arguments are null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	public static <T extends JsonNode> JsonNodeValidator<T> requireThat(T value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

	/**
	 * Returns the configuration used by new validators.
	 *
	 * @return the configuration used by new validators
	 */
	@CheckReturnValue
	public static Configuration configuration()
	{
		return DELEGATE.configuration();
	}

	/**
	 * Updates the configuration that will be used by new validators.
	 * <p>
	 * <b>NOTE</b>: Changes are only applied when {@link ConfigurationUpdater#close()} is invoked.
	 *
	 * @return the configuration updater
	 */
	@CheckReturnValue
	public static ConfigurationUpdater updateConfiguration()
	{
		return DELEGATE.updateConfiguration();
	}

	/**
	 * Updates the configuration that will be used by new validators, using a fluent API that automatically
	 * applies the changes on exit. For example:
	 * {@snippet :
	 * validators.apply(v -> v.updateConfiguration().allowDiff(false)).
	 * requireThat(value, name);
	 *}
	 *
	 * @param consumer the configuration updater
	 * @return this
	 * @throws NullPointerException if {@code consumer} is null
	 */
	public static JacksonValidators updateConfiguration(Consumer<ConfigurationUpdater> consumer)
	{
		return DELEGATE.updateConfiguration(consumer);
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
	 *
	 * @return an unmodifiable map from each entry's name to its value
	 */
	public static Map<String, Object> getContext()
	{
		return CONTEXT_LOCK.optimisticRead(DELEGATE::getContext);
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
	 * @throws NullPointerException if {@code name} is null
	 */
	public static JacksonValidators withContext(Object value, String name)
	{
		try (CloseableLock unused = CONTEXT_LOCK.write())
		{
			return DELEGATE.withContext(value, name);
		}
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
	public static JacksonValidators removeContext(String name)
	{
		try (CloseableLock unused = CONTEXT_LOCK.write())
		{
			return DELEGATE.removeContext(name);
		}
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

	private DefaultJacksonValidators()
	{
	}
}